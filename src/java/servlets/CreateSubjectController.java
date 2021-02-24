/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.AdminDAO;
import dtos.SubjectDTO;
import dtos.UserDTO;
import java.io.IOException;
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
@WebServlet(name = "CreateSubjectController", urlPatterns = {"/CreateSubjectController"})
public class CreateSubjectController extends HttpServlet {

    private static final String ERROR = "createSubject.jsp";
    private static final String SUCCESS = "ManagementController";

    private static final Logger LOGGER = Logger.getLogger(CreateSubjectController.class);

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
            String subjectName = request.getParameter("txtName");
            String numOfQuestionStr = request.getParameter("txtNumOfQuestion");
            String timeLimitStr = request.getParameter("txtTimeLimit");
            String statusStr = request.getParameter("cbStatus");

            boolean emptyAll = subjectID == null && subjectName == null && numOfQuestionStr == null && timeLimitStr == null && statusStr == null;
            AdminDAO dao = new AdminDAO();
            if (!emptyAll) {

                boolean empty = false;
                int numOfQuestion = 0;
                int timeLimit = 0;

                if ("".equals(subjectID)) {
                    request.setAttribute("ERROR_SUBJECT_ID", "Subject ID cannot be empty!");
                    empty = true;
                }
                if ("".equals(subjectName)) {
                    request.setAttribute("ERROR_SUBJECT_NAME", "Subject Name cannot be empty!");
                    empty = true;
                }
                if ("".equals(numOfQuestionStr)) {
                    request.setAttribute("ERROR_NUM_OF_QUESTION", "Number of question cannot be empty!");
                    empty = true;
                } else {
                    try {
                        numOfQuestion = Integer.parseInt(numOfQuestionStr);
                    } catch (NumberFormatException e) {
                        request.setAttribute("ERROR_NUM_OF_QUESTION", "Number of question must be integer value!");
                        empty = true;
                    }
                }
                
                if ("".equals(timeLimitStr)) {
                    request.setAttribute("ERROR_TIME_LIMIT", "Limit time cannot be empty!");
                    empty = true;
                } else {
                    try {
                        timeLimit = Integer.parseInt(timeLimitStr);
                    } catch (NumberFormatException e) {
                        request.setAttribute("ERROR_TIME_LIMIT", "Limit time must be integer value!");
                        empty = true;
                    }
                }

                if (!empty) {
                    boolean status = false;
                    if ("active".equals(statusStr)) {
                        status = true;
                    }
                    SubjectDTO subject = new SubjectDTO(subjectID, subjectName, numOfQuestion, timeLimit, status, null);
                    HttpSession session = request.getSession();
                    String userID = ((UserDTO) session.getAttribute("LOGIN_USERDTO")).getEmail();
                    dao.createSubject(userID, subject);
                    url = SUCCESS;
                }
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
