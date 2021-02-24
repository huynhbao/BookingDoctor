/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.AdminDAO;
import dtos.SearchDTO;
import dtos.SubjectDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import utils.MyUtils;

/**
 *
 * @author HuynhBao
 */
@WebServlet(name = "ManagementController", urlPatterns = {"/ManagementController"})
public class ManagementController extends HttpServlet {

    private static final String ERROR = "management.jsp";
    private static final String SUCCESS = "management.jsp";
    private static final Logger LOGGER = Logger.getLogger(ManagementController.class);

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

            String chkSubject = request.getParameter("chkSubject");
            String chkName = request.getParameter("chkName");
            String chkStatus = request.getParameter("chkStatus");
            String page = request.getParameter("page");

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
            if (chkSubject == null) {
                chkSubject = "";
            }
            if (chkName == null) {
                chkName = "";
            }
            if (chkStatus == null) {
                chkStatus = "";
            }
            boolean emptySearch = chkSubject.isEmpty() && chkName.isEmpty() && chkStatus.isEmpty();
            AdminDAO dao = new AdminDAO();
            List<SearchDTO> searchFilter = new ArrayList<>();
            Map<Integer, List<SubjectDTO>> listSearch = new HashMap<>();
            if (!emptySearch) {

                if ("on".equals(chkSubject)) {
                    searchFilter.add(new SearchDTO("subject", 0, "", request.getParameter("cbSubject")));
                }
                if ("on".equals(chkName)) {
                    searchFilter.add(new SearchDTO("name", 0, "", request.getParameter("txtSearchName")));
                }
                if ("on".equals(chkStatus)) {
                    boolean status = false;
                    if ("active".equals(request.getParameter("cbStatus"))) {
                        status = true;
                    }
                    searchFilter.add(new SearchDTO("status", 0, "", String.valueOf(status)));
                }

                if (!searchFilter.isEmpty()) {
                    listSearch = dao.searchQuestion(currentPage, searchFilter);
                    int rows = listSearch.keySet().stream().findFirst().get();
                    int nOfPages = (int) Math.ceil(rows / (double) MyUtils.recordPerPage);
                    request.setAttribute("LIST", listSearch.get(rows));
                    request.setAttribute("noOfPages", nOfPages);
                    request.setAttribute("currentPage", currentPage);
                }
            }
            
            List<SubjectDTO> list = dao.getSubjectsAdmin();
            request.setAttribute("LIST_SUBJECT", list);
            url = SUCCESS;
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
