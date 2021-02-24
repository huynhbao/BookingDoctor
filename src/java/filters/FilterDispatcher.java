/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dtos.UserDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import utils.MyUtils;

/**
 *
 * @author HuynhBao
 */
public class FilterDispatcher implements Filter {

    private final List<String> STUDENT;
    private final List<String> ADMIN;

    private static final boolean debug = true;
    private final String LOGIN_PAGE = "login.jsp";
    private final String INVALID_PAGE = "invalid.jsp";

    private static final Logger LOGGER = Logger.getLogger(FilterDispatcher.class);

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public FilterDispatcher() {

        STUDENT = new ArrayList<>();
        STUDENT.add("QuizController");
        STUDENT.add("StudentController");
        STUDENT.add("ReviewController");
        STUDENT.add("SubmitController");
        STUDENT.add("LogoutController");
        STUDENT.add("HistoryController");

        ADMIN = new ArrayList<>();
        ADMIN.add("ManagementController");
        ADMIN.add("CreateQuestionController");
        ADMIN.add("UpdateQuestionController");
        ADMIN.add("DeleteQuestionController");
        ADMIN.add("CreateSubjectController");
        ADMIN.add("UpdateSubjectController");
        ADMIN.add("DeleteSubjectController");
        ADMIN.add("LogoutController");
    }


    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        String url = LOGIN_PAGE;

        try {
            int lastIndex = uri.lastIndexOf("/");
            String resource = uri.substring(lastIndex + 1);

            if (resource.length() > 0) {
                url = resource.substring(0, 1).toUpperCase() + resource.substring(1) + "Controller";
                if (resource.lastIndexOf(".jpg") > 0 || resource.lastIndexOf(".css") > 0) {
                    url = null;
                }
            }

            if (url != null) {
                HttpSession session = req.getSession();
                if (session == null || session.getAttribute("LOGIN_USERDTO") == null) {
                    if (url.contains("Login") || url.contains("Register")) {
                    } else {
                        url = LOGIN_PAGE;
                    }
                } else {

                    UserDTO user = (UserDTO) session.getAttribute("LOGIN_USERDTO");
                    String roleID = user.getRoleID();
                    if (roleID.equals(MyUtils.ADMIN_ROLLE) && ADMIN.contains(url)) {
                    } else if (roleID.equals(MyUtils.STUDENT_ROLLE) && STUDENT.contains(url)) {
                    } else {
                        if (url.contains("Login")  || url.contains("Register")) {
                            if (roleID.equals(MyUtils.ADMIN_ROLLE)) {
                                url = STUDENT.get(0);
                            } else if (roleID.equals(MyUtils.STUDENT_ROLLE)) {
                                url = STUDENT.get(1);
                            }
                        } else {
                            url = INVALID_PAGE;
                        }
                        
                    }

                }

                req.setCharacterEncoding("UTF-8");
                RequestDispatcher rd = req.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("FilterDispatcher:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("FilterDispatcher()");
        }
        StringBuffer sb = new StringBuffer("FilterDispatcher(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
