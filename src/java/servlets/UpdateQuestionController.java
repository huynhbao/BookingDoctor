/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.AdminDAO;
import dtos.QuestionDTO;
import dtos.SubjectDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "UpdateQuestionController", urlPatterns = {"/UpdateQuestionController"})
public class UpdateQuestionController extends HttpServlet {

    private static final String ERROR = "update.jsp";
    private static final String SUCCESS = "ManagementController";
    private static final String ADMIN_PAGE = "ManagementController";

    private static final Logger LOGGER = Logger.getLogger(UpdateQuestionController.class);

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
            String questionIDStr = request.getParameter("txtQuestionID");
            AdminDAO dao = new AdminDAO();

            if (questionIDStr != null || !"".equals(questionIDStr)) {
                int questionID = 0;
                QuestionDTO question = null;
                try {
                    questionID = Integer.parseInt(questionIDStr);
                    question = dao.getQuestion(questionID);
                } catch (NumberFormatException e) {
                    url = ADMIN_PAGE;
                }

                if (question != null) {

                    String questionName = request.getParameter("txtName");
                    String answerA = request.getParameter("txtAnswerA");
                    String answerB = request.getParameter("txtAnswerB");
                    String answerC = request.getParameter("txtAnswerC");
                    String answerD = request.getParameter("txtAnswerD");
                    String subject = request.getParameter("cbSubject");
                    String statusStr = request.getParameter("cbStatus");

                    boolean emptyAll = questionName == null && answerA == null && answerB == null && answerC == null && answerD == null && subject == null && statusStr == null;

                    if (!emptyAll) {
                        String correctAnswerStr = request.getParameter("radioCorrect");
                        int correctAnswer = 0;
                        boolean empty = false;

                        if (correctAnswerStr != null) {
                            try {
                                correctAnswer = Integer.parseInt(correctAnswerStr);
                            } catch (NumberFormatException e) {
                                request.setAttribute("ERROR_ANSWER", "You didn't select correct answer");
                                empty = true;
                            }
                        } else {
                            request.setAttribute("ERROR_ANSWER", "You didn't select correct answer");
                            empty = true;
                        }

                        if ("".equals(questionName)) {
                            request.setAttribute("ERROR_QUESTION_NAME", "Question Name cannot be empty!");
                            empty = true;
                        }
                        if ("".equals(answerA)) {
                            request.setAttribute("ERROR_ANSWER_A", "Answer A cannot be empty!");
                            empty = true;
                        }
                        if ("".equals(answerB)) {
                            request.setAttribute("ERROR_ANSWER_B", "Answer B cannot be empty!");
                            empty = true;
                        }
                        if ("".equals(answerC)) {
                            request.setAttribute("ERROR_ANSWER_C", "Answer C cannot be empty!");
                            empty = true;
                        }
                        if ("".equals(answerD)) {
                            request.setAttribute("ERROR_ANSWER_D", "Answer D cannot be empty!");
                            empty = true;
                        }

                        if (!empty) {
                            boolean status = false;
                            if ("active".equals(statusStr)) {
                                status = true;
                            }
                            question.setName(questionName.trim());
                            question.setAnswerA(answerA.trim());
                            question.setAnswerB(answerB.trim());
                            question.setAnswerC(answerC.trim());
                            question.setAnswerD(answerD.trim());
                            question.setCorrectAnswer(correctAnswer);
                            question.setStatus(status);
                            question.setSubjectID(subject);
                            
                            HttpSession session = request.getSession();
                            String userID = ((UserDTO) session.getAttribute("LOGIN_USERDTO")).getEmail();
                            dao.updateQuestion(userID, question);
                            url = SUCCESS;
                        }
                    }

                    request.setAttribute("QUESTION", question);
                }
            } else {
                url = SUCCESS;
            }

            List<SubjectDTO> list = dao.getSubjectsAdmin();

            request.setAttribute("LIST_SUBJECT", list);
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
