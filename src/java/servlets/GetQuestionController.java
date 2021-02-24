/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.UserDAO;
import dtos.SubjectDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author HuynhBao
 */
@WebServlet(name = "GetQuestionController", urlPatterns = {"/GetQuestionController"})
public class GetQuestionController extends HttpServlet {

    private static final String ERROR = "ManagementController";
    private static final String SUCCESS = "question.jsp";

    private static final Logger LOGGER = Logger.getLogger(GetQuestionController.class);

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

            int currentPage = 1;

            if (page != null) {
                if ("".equals(page)) {
                    currentPage = 1;
                } else {
                    currentPage = Integer.parseInt(page);
                    if (currentPage == 0) {
                        currentPage = 1;
                    }
                }
            }

            UserDAO userDAO = new UserDAO();
            SubjectDTO subject = userDAO.getSubject(subjectID);

            if (subject != null) {
                /*AdminDAO dao = new AdminDAO();
                Map<Integer, SubjectDTO> list = dao.getQuestionList(currentPage, subject);
                int rows = list.keySet().stream().findFirst().get();
                int nOfPages = (int) Math.ceil(rows / (double) 2);
                
                request.setAttribute("SUBJECT", subject);
                request.setAttribute("noOfPages", nOfPages);
                request.setAttribute("currentPage", currentPage);
                url = SUCCESS;*/
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
