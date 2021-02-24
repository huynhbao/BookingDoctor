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
import java.util.Date;
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
@WebServlet(name = "CreateQuestionController", urlPatterns = {"/CreateQuestionController"})
public class CreateQuestionController extends HttpServlet {

    private static final String ERROR = "create.jsp";
    private static final String SUCCESS = "ManagementController";

    private static final Logger LOGGER = Logger.getLogger(CreateQuestionController.class);

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
            String questionName = request.getParameter("txtName");
            String answerA = request.getParameter("txtAnswerA");
            String answerB = request.getParameter("txtAnswerB");
            String answerC = request.getParameter("txtAnswerC");
            String answerD = request.getParameter("txtAnswerD");
            String subject = request.getParameter("cbSubject");
            String statusStr = request.getParameter("cbStatus");

            boolean emptyAll = questionName == null && answerA == null && answerB == null && answerC == null && answerD == null && subject == null && statusStr == null;
            AdminDAO dao = new AdminDAO();
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
                    Date curDate = new Date();
                    if ("active".equals(statusStr)) {
                        status = true;
                    }
                    QuestionDTO question = new QuestionDTO(0, questionName.trim(), answerA.trim(), answerB.trim(), answerC.trim(), answerD.trim(), correctAnswer, 0, curDate, status, subject);
                    HttpSession session = request.getSession();
                    String userID = ((UserDTO) session.getAttribute("LOGIN_USERDTO")).getEmail();
                    dao.createQuestion(userID, question);
                    url = SUCCESS;
                }
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
