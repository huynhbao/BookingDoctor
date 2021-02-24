/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.QuestionDTO;
import dtos.SearchDTO;
import dtos.SubjectDTO;
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
public class AdminDAO {

    private static final Logger LOGGER = Logger.getLogger(AdminDAO.class);

    public List<SubjectDTO> getSubjectsAdmin() throws SQLException {
        List<SubjectDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT subjectID, name, time_limit, num_of_question, status FROM tblSubjects";
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

    public Map<Integer, List<SubjectDTO>> searchQuestion(int currentPage, List<SearchDTO> searchFilter) throws SQLException, NamingException {
        Map<Integer, List<SubjectDTO>> result = new HashMap<>();
        List<SubjectDTO> listSubject = null;

        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(*) OVER(), q.questionID, subjectID, name, answerA, answerB, answerC, answerD, correct_answer, status FROM tblQuestions q JOIN tblAnswers a ON q.questionID = a.questionID WHERE name LIKE ?";
                int count = 2;
                String searchName = "";
                for (SearchDTO search : searchFilter) {
                    if (search.getName().equals("subject")) {
                        search.setIndex(count++);
                        sql += " AND subjectID = ?";
                    } else if (search.getName().equals("name")) {
                        searchName = search.getValue();
                    } else if (search.getName().equals("status")) {
                        search.setIndex(count++);
                        sql += " AND status = ?";

                    }
                }

                sql = sql.concat(" ORDER BY name ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

                stm = conn.prepareStatement(sql);

                for (SearchDTO search : searchFilter) {
                    if (search.getName().equals("status")) {
                        stm.setBoolean(search.getIndex(), Boolean.parseBoolean(search.getValue()));
                    } else if (!search.getName().equals("name")) {
                        stm.setString(search.getIndex(), search.getValue());
                    }
                }
                stm.setString(1, "%" + searchName + "%");

                int start = currentPage * MyUtils.recordPerPage - MyUtils.recordPerPage;
                UserDAO dao = new UserDAO();
                stm.setInt(count++, start);
                stm.setInt(count++, MyUtils.recordPerPage);
                int totalRow = 0;
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listSubject == null) {
                        listSubject = new ArrayList<>();
                    }
                    if (totalRow == 0) {
                        totalRow = rs.getInt(1);
                    }
                    int questionID = rs.getInt(2);
                    String subjectID = rs.getString("subjectID");

                    QuestionDTO question = getQuestion(questionID);

                    int index = -1;
                    for (int i = 0; i < listSubject.size(); i++) {
                        if (listSubject.get(i).getSubjectID().equals(subjectID)) {
                            index = i;
                        }
                    }
                    if (index == -1) {
                        SubjectDTO subject = dao.getSubject(subjectID);
                        List<QuestionDTO> listQuestion = new ArrayList<>();
                        subject.setQuestion(listQuestion);
                        listSubject.add(subject);
                        index = listSubject.size() - 1;
                    }

                    listSubject.get(index).getQuestion().add(question);
                }
                result.put(totalRow, listSubject);
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

    public void createQuestion(String userID, QuestionDTO question) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String questionID[] = {"questionID"};
                String sql = "INSERT INTO tblQuestions(name, subjectID, createDate, status, modifiedDate, modifiedUser) VALUES(?,?,?,?,?,?)";
                stm = conn.prepareStatement(sql, questionID);
                Timestamp createDate = new Timestamp(question.getCreateDate().getTime());

                stm.setString(1, question.getName());
                stm.setString(2, question.getSubjectID());
                stm.setTimestamp(3, createDate);
                stm.setBoolean(4, question.getStatus());
                Timestamp modifiedDate = new Timestamp(new Date().getTime());
                stm.setTimestamp(5, modifiedDate);
                stm.setString(6, userID);

                stm.executeUpdate();
                rs = stm.getGeneratedKeys();

                if (rs.next()) {
                    String sqlQuestion = "INSERT INTO tblAnswers(questionID, answerA, answerB, answerC, answerD, correct_answer) VALUES(?,?,?,?,?,?)";
                    stm = conn.prepareStatement(sqlQuestion);
                    stm.setInt(1, rs.getInt(1));
                    stm.setString(2, question.getAnswerA());
                    stm.setString(3, question.getAnswerB());
                    stm.setString(4, question.getAnswerC());
                    stm.setString(5, question.getAnswerD());
                    stm.setInt(6, question.getCorrectAnswer());
                    stm.executeUpdate();
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

    }

    public SubjectDTO getQuestionList(SubjectDTO subject) throws SQLException {
        List<QuestionDTO> list = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT q.questionID, name, answerA, answerB, answerC, answerD, correct_answer, createDate, status FROM tblQuestions q JOIN tblAnswers a ON q.questionID = a.questionID WHERE subjectID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, subject.getSubjectID());
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
                    int correctAnswer = rs.getInt("correct_answer");
                    Date createDate = rs.getTimestamp("createDate");
                    boolean status = rs.getBoolean("status");

                    QuestionDTO question = new QuestionDTO(questionID, questionName, answerA, answerB, answerC, answerD, correctAnswer, 0, createDate, status, subject.getSubjectID());
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

    public QuestionDTO getQuestion(int questionID) throws SQLException {
        QuestionDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT subjectID, name, answerA, answerB, answerC, answerD, correct_answer, createDate, status FROM tblQuestions q JOIN tblAnswers a ON q.questionID = a.questionID WHERE q.questionID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, questionID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String subjectID = rs.getString("subjectID");
                    String questionName = rs.getString("name");
                    String answerA = rs.getString("answerA");
                    String answerB = rs.getString("answerB");
                    String answerC = rs.getString("answerC");
                    String answerD = rs.getString("answerD");
                    Date createDate = rs.getTimestamp("createDate");
                    int correctAnswer = rs.getInt("correct_answer");
                    boolean status = rs.getBoolean("status");

                    result = new QuestionDTO(questionID, questionName, answerA, answerB, answerC, answerD, correctAnswer, 0, createDate, status, subjectID);
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

    public int getAnswer(int questionID) throws SQLException {
        int answer = 0;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT correct_answer FROM tblQuestions q JOIN tblAnswers a ON q.questionID = a.questionID WHERE q.questionID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, questionID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int correctAnswer = rs.getInt("correct_answer");
                    answer = correctAnswer;
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

        return answer;
    }

    public void updateQuestion(String userID, QuestionDTO question) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblQuestions SET name = ?, subjectID = ?, status = ?, modifiedDate = ?, modifiedUser = ? WHERE questionID = ?";
                stm = conn.prepareStatement(sql);

                stm.setString(1, question.getName());
                stm.setString(2, question.getSubjectID());
                stm.setBoolean(3, question.getStatus());
                Timestamp modifiedDate = new Timestamp(new Date().getTime());
                stm.setTimestamp(4, modifiedDate);
                stm.setString(5, userID);
                stm.setInt(6, question.getQuestionID());

                boolean result = stm.executeUpdate() == 1;

                if (result) {
                    String sqlAnswer = "UPDATE tblAnswers SET answerA = ?, answerB = ?, answerC = ?, answerD = ?, correct_answer = ? WHERE questionID = ?";
                    stm = conn.prepareStatement(sqlAnswer);
                    stm.setString(1, question.getAnswerA());
                    stm.setString(2, question.getAnswerB());
                    stm.setString(3, question.getAnswerC());
                    stm.setString(4, question.getAnswerD());
                    stm.setInt(5, question.getCorrectAnswer());
                    stm.setInt(6, question.getQuestionID());
                    stm.executeUpdate();
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public void deleteQuestion(int questionID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblQuestions SET status = ? WHERE questionID = ?";
                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, false);
                stm.setInt(2, questionID);
                stm.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }
    
    public void createSubject(String userID, SubjectDTO subject) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblSubjects(subjectID, name, time_limit, num_of_question, modifiedDate, modifiedUser, status) VALUES(?,?,?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                Date curDate = new Date();
                Timestamp createDate = new Timestamp(curDate.getTime());

                stm.setString(1, subject.getSubjectID());
                stm.setString(2, subject.getName());
                stm.setInt(3, subject.getTime_limit());
                stm.setInt(4, subject.getNumOfQuestion());
                stm.setTimestamp(5, createDate);
                stm.setString(6, userID);
                stm.setBoolean(7, subject.getStatus());

                stm.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public void updateSubject(String userID, SubjectDTO subject) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblSubjects SET name = ?, time_limit = ?, num_of_question = ?, modifiedDate = ?, modifiedUser = ?, status = ? WHERE subjectID = ?";
                stm = conn.prepareStatement(sql);

                stm.setString(1, subject.getName());
                stm.setInt(2, subject.getTime_limit());
                stm.setInt(3, subject.getNumOfQuestion());
                Timestamp modifiedDate = new Timestamp(new Date().getTime());
                stm.setTimestamp(4, modifiedDate);
                stm.setString(5, userID);
                stm.setBoolean(6, subject.getStatus());
                stm.setString(7, subject.getSubjectID());

                stm.executeUpdate();

            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public void deleteSubject(String subjectID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblSubjects SET status = ? WHERE subjectID = ?";
                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, false);
                stm.setString(2, subjectID);
                stm.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }
}
