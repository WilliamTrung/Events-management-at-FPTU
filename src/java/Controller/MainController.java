/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DTO.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 *
 * @author WilliamTrung
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String USER = "";
    private final String LOGOUT = "LogoutController";
    private final String ROLES_UPDATE_SELF = "UpdateSelfController";
    private final String ADMIN_SEARCH_USER = "ViewUserController";
    private final String ADMIN_CHANGE_STATUS = "ChangeStatusController";
    private final String ADMIN_UPDATE_USER = "UpdateUserController";
    private final String USER_SEARCH_EVENT = "ViewEventController";
    private final String EM_CREATE_EVENT = "CreateEventController";
    private final String EM_CREATE_EVENT_IDLE = "CreateEventIdleController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url = ERROR;
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");
        try {
            if (action.equals("LoadUsers")) {
                if (user.getRole().equals("Admin")) {
                    session.setAttribute("MODE", "ADMIN_MODE");
                }
                url = ADMIN_SEARCH_USER;
            } else if (action.equals("LoadEvents")) {
                if (user.getRole().equals("Admin")) {
                    session.setAttribute("MODE", "USER_MODE");
                }
                url = USER_SEARCH_EVENT;
            } else if (action.equals("Logout")) {
                url = LOGOUT;
            } else if (action.equals("UpdateUserName")) {
                url = ROLES_UPDATE_SELF;
            } else if (action.equals("Confirm Update")) {
                url = ADMIN_UPDATE_USER;
            } else if (action.equals("ChangeStatus")) {
                url = ADMIN_CHANGE_STATUS;
            } else if (action.equals("CreateEvent")) {
                url = EM_CREATE_EVENT;
            } else if (action.equals("Choose Slot")) {
                url = EM_CREATE_EVENT_IDLE;
            } 
        } catch (Exception e) {
            request.setAttribute("ERROR_MESSAGE", "An error has occured in MainController!");
            log("Error at MainController: " + e.toString());
        }
        request.getRequestDispatcher(url).forward(request, response);
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
