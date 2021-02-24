/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.UserDAO;
import dtos.QuizDTO;
import dtos.SubjectDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author HuynhBao
 */
@WebServlet(name = "QuizController", urlPatterns = {"/QuizController"})
public class QuizController extends HttpServlet {

    
    private static final String INVALID = "invalid.jsp";
    private static final String ERROR = "StudentController";
    private static final String SUCCESS = "quiz.jsp";

    private static final Logger LOGGER = Logger.getLogger(QuizController.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String subjectID = request.getParameter("txtSubjectID");
            String page = request.getParameter("page");
            String saveAnswer = request.getParameter("saveAnswer");
            int currentPage = 1;

            if (page != null) {
                if ("".equals(page)) {
                    currentPage = 1;
                } else {
                    currentPage = Integer.parseInt(page);
                    if (currentPage <= 0) {
                        currentPage = 1;
                    }
                }
            }

            UserDAO dao = new UserDAO();
            SubjectDTO subject = dao.getSubject(subjectID);

            if (subject != null) {

                HttpSession session = request.getSession();
                QuizDTO quiz = (QuizDTO) session.getAttribute("QUIZ");
                UserDTO user = (UserDTO) session.getAttribute("LOGIN_USERDTO");

                if (saveAnswer != null && "true".equals(saveAnswer)) {
                    String studentAnswerStr = request.getParameter("answer");
                    int studentAnswer = Integer.parseInt(studentAnswerStr);
                    //quiz.getQuiz().get(subjectID).getQuestion().get(currentPage - 1).setUserAnswer(studentAnswer);
                    quiz.getSubject().getQuestion().get(currentPage - 1).setUserAnswer(studentAnswer);
                }

                if (quiz == null) {
                    quiz = new QuizDTO();
                }

                if (page == null) {
                    Calendar now = Calendar.getInstance();
                    Date curDate = now.getTime();
                    quiz.setStartTime(curDate);
                    now.add(Calendar.MINUTE, subject.getTime_limit());
                    quiz.setEndTime(now.getTime());
                    quiz.setSubject(dao.generateQuestion(subject));
                    quiz.setUserID(user.getEmail());
                } else {
                    if (currentPage > quiz.getSubject().getNumOfQuestion()) {
                        currentPage = 1;
                    }
                }

                if (quiz.getSubject().getQuestion() == null || subject.getNumOfQuestion() > quiz.getSubject().getQuestion().size()) {
                    url = INVALID;
                    request.setAttribute("MSG", "There are not enough questions in the subjects you choose!");
                } else {
                    session.setAttribute("QUIZ", quiz);
                    request.setAttribute("QUIZ_QUESTION", quiz.getSubject().getQuestion().get(currentPage - 1));
                    request.setAttribute("noOfPages", quiz.getSubject().getNumOfQuestion());
                    request.setAttribute("currentPage", currentPage);
                    url = SUCCESS;
                }
            } else {
                url = ERROR;
            }

        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
