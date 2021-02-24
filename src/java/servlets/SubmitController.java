/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.AdminDAO;
import daos.UserDAO;
import dtos.QuestionDTO;
import dtos.QuizDTO;
import java.io.IOException;
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
@WebServlet(name = "SubmitController", urlPatterns = {"/SubmitController"})
public class SubmitController extends HttpServlet {

    private static final String ERROR = "StudentController";
    private static final String SUCCESS = "ReviewController";

    private static final Logger LOGGER = Logger.getLogger(SubmitController.class);

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
            //String subjectID = request.getParameter("txtSubjectID");
            HttpSession session = request.getSession();
            QuizDTO quiz = (QuizDTO) session.getAttribute("QUIZ");
            

            if (quiz != null) {
                int totalCorrect = 0;
                AdminDAO adminDAO = new AdminDAO();
                UserDAO userDAO = new UserDAO();
                for (QuestionDTO question : quiz.getSubject().getQuestion()) {
                    int correctAnswer = adminDAO.getAnswer(question.getQuestionID());
                    if (correctAnswer == question.getUserAnswer()) {
                        totalCorrect++;
                    }
                }
                
                Date now = new Date();
                float totalPoints = (float) totalCorrect * 10 / (float) quiz.getSubject().getNumOfQuestion();
                quiz.setNumOfCorrect(totalCorrect);
                quiz.setPoints(totalPoints);
                quiz.setEndTime(now);
                int quizID = userDAO.storeQuizDetail(quiz);
                session.setAttribute("QUIZ", null);
                url = SUCCESS + "?txtQuizID=" + quizID;
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
