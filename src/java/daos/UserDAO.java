/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.QuestionDTO;
import dtos.QuizDTO;
import dtos.SearchDTO;
import dtos.SubjectDTO;
import dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import utils.DBUtils;
import utils.MyUtils;

/**
 *
 * @author HuynhBao
 */
public class UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);

    public UserDTO checkLogin(String email, String password) throws SQLException {
        UserDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT name, email, status, roleID FROM tblUsers WHERE email = ? AND password = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                rs = stm.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    String roleID = rs.getString("roleID");
                    result = new UserDTO(email, name, "", status, roleID);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public void register(UserDTO user) throws SQLException, ClassNotFoundException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblUsers(email, name, password, status, roleID) VALUES(?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, user.getEmail());
                stm.setString(2, user.getName());
                stm.setString(3, user.getPassword());
                stm.setString(4, user.getStatus());
                stm.setString(5, user.getRoleID());
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public SubjectDTO getSubject(String subjectID) throws SQLException {
        SubjectDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT name, time_limit, num_of_question, status FROM tblSubjects WHERE subjectID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, subjectID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    int time_limit = rs.getInt("time_limit");
                    int numOfQuestion = rs.getInt("num_of_question");
                    boolean status = rs.getBoolean("status");
                    result = new SubjectDTO(subjectID, name, numOfQuestion, time_limit, status, null);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public SubjectDTO generateQuestion(SubjectDTO subject) throws SQLException {
        List<QuestionDTO> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT TOP (?) q.questionID, name, answerA, answerB, answerC, answerD FROM tblQuestions q JOIN tblAnswers a ON q.questionID = a.questionID WHERE subjectID = ? AND status = 1 ORDER BY NEWID()";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, subject.getNumOfQuestion());
                stm.setString(2, subject.getSubjectID());
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    int questionID = rs.getInt(1);
                    String questionName = rs.getString("name");
                    String answerA = rs.getString("answerA");
                    String answerB = rs.getString("answerB");
                    String answerC = rs.getString("answerC");
                    String answerD = rs.getString("answerD");

                    QuestionDTO question = new QuestionDTO(questionID, questionName, answerA, answerB, answerC, answerD, 0, 0, null, true, subject.getSubjectID());
                    list.add(question);
                }
                subject.setQuestion(list);
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return subject;
    }

    public int storeQuizDetail(QuizDTO quiz) throws SQLException {
        int quizID = -1;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String quizIDArr[] = {"quizID"};
                String sql = "INSERT INTO tblQuizzes(userID, subjectID, start_time, end_time, num_of_correct_answer, points) VALUES(?,?,?,?,?,?)";
                stm = conn.prepareStatement(sql, quizIDArr);
                Timestamp startTime = new Timestamp(quiz.getStartTime().getTime());
                Timestamp endTime = new Timestamp(quiz.getEndTime().getTime());

                stm.setString(1, quiz.getUserID());
                stm.setString(2, quiz.getSubject().getSubjectID());
                stm.setTimestamp(3, startTime);
                stm.setTimestamp(4, endTime);
                stm.setInt(5, quiz.getNumOfCorrect());
                stm.setFloat(6, quiz.getPoints());

                stm.executeUpdate();
                rs = stm.getGeneratedKeys();

                if (rs.next()) {
                    quizID = rs.getInt(1);
                    String sqlQuizDetail = "INSERT INTO tblQuizDetails(quizID, questionID, user_answer) VALUES(?,?,?)";
                    stm = conn.prepareStatement(sqlQuizDetail);
                    for (QuestionDTO question : quiz.getSubject().getQuestion()) {
                        stm.setInt(1, quizID);
                        stm.setInt(2, question.getQuestionID());
                        stm.setInt(3, question.getUserAnswer());
                        stm.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return quizID;
    }

    public List<QuizDTO> getHistoryList(UserDTO user) throws SQLException {
        List<QuizDTO> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT quizID, q.subjectID, name, start_time, num_of_correct_answer, points FROM tblQuizzes q JOIN tblSubjects s ON q.subjectID = s.subjectID WHERE userID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, user.getEmail());
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    int quizID = rs.getInt(1);
                    String subjectID = rs.getString(2);
                    String subjectName = rs.getString("name");
                    Date startTime = rs.getTimestamp("start_time");
                    int numOfCorrect = rs.getInt("num_of_correct_answer");
                    float points = rs.getFloat("points");
                    SubjectDTO subject = new SubjectDTO(subjectID, subjectName, 0, 0, true, null);
                    QuizDTO quiz = new QuizDTO(quizID, subject, startTime, null, numOfCorrect, points, user.getEmail());
                    list.add(quiz);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    public Map<Integer, List<QuizDTO>> searchHistory(int currentPage, List<SearchDTO> searchFilter) throws SQLException, NamingException {
        Map<Integer, List<QuizDTO>> result = new HashMap<>();
        List<QuizDTO> list = null;

        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(*) OVER(), quizID, q.subjectID, name, start_time, num_of_correct_answer, points FROM tblQuizzes q JOIN tblSubjects s ON q.subjectID = s.subjectID WHERE userID = ? AND name LIKE ? ORDER BY start_time DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

                stm = conn.prepareStatement(sql);

                for (SearchDTO search : searchFilter) {
                    stm.setString(search.getIndex(), search.getValue());
                }

                int start = currentPage * MyUtils.recordPerPage - MyUtils.recordPerPage;
                stm.setInt(3, start);
                stm.setInt(4, MyUtils.recordPerPage);
                int totalRow = 0;
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    if (totalRow == 0) {
                        totalRow = rs.getInt(1);
                    }
                    int quizID = rs.getInt("quizID");
                    String subjectID = rs.getString(3);
                    String subjectName = rs.getString("name");
                    Date startTime = rs.getTimestamp("start_time");
                    int numOfCorrect = rs.getInt("num_of_correct_answer");
                    float points = rs.getFloat("points");
                    SubjectDTO subject = new SubjectDTO(subjectID, subjectName, 0, 0, true ,null);
                    QuizDTO quiz = new QuizDTO(quizID, subject, startTime, null, numOfCorrect, points, searchFilter.get(0).getValue());
                    list.add(quiz);
                }
                result.put(totalRow, list);
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;

    }

    public QuizDTO getHistory(int quizID, String userID) throws SQLException {
        QuizDTO quiz = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT subjectID, start_time, end_time, num_of_correct_answer, points FROM tblQuizzes WHERE quizID = ? AND userID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, quizID);
                stm.setString(2, userID);
                AdminDAO dao = new AdminDAO();
                rs = stm.executeQuery();
                if (rs.next()) {
                    String subjectID = rs.getString("subjectID");
                    Date startTime = rs.getTimestamp("start_time");
                    Date endTime = rs.getTimestamp("end_time");
                    int numOfCorrect = rs.getInt("num_of_correct_answer");
                    float points = rs.getFloat("points");

                    SubjectDTO subject = getSubject(subjectID);
                    List<QuestionDTO> listQuestion = new ArrayList<>();
                    String sqlDetail = "SELECT questionID, user_answer FROM tblQuizDetails WHERE quizID = ?";
                    stm = conn.prepareStatement(sqlDetail);
                    stm.setInt(1, quizID);
                    rs = stm.executeQuery();
                    while (rs.next()) {
                        QuestionDTO question = dao.getQuestion(rs.getInt(1));
                        question.setUserAnswer(rs.getInt("user_answer"));
                        listQuestion.add(question);
                    }
                    subject.setQuestion(listQuestion);
                    quiz = new QuizDTO(quizID, subject, startTime, endTime, numOfCorrect, points, userID);

                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return quiz;
    }
    
    public List<SubjectDTO> getSubjectsUser() throws SQLException {
        List<SubjectDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT subjectID, name, time_limit, num_of_question, status FROM tblSubjects WHERE status = 1";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String subjectID = rs.getString("subjectID");
                    String name = rs.getString("name");
                    int time_limit = rs.getInt("time_limit");
                    int numOfQuestion = rs.getInt("num_of_question");
                    boolean status = rs.getBoolean("status");
                    SubjectDTO subject = new SubjectDTO(subjectID, name, numOfQuestion, time_limit, status, null);
                    result.add(subject);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }
}
