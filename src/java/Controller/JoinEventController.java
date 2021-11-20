/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.FollowedEventDAO;
import DAO.JoinEventDAO;
import DTO.EventDTO;
import DTO.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author WilliamTrung
 */
@WebServlet(name = "JoinEventController", urlPatterns = {"/JoinEventController"})
public class JoinEventController extends HttpServlet {

    private final String SUCCESS = "viewEventDetails.jsp";
    private final String FAIL = "viewEventDetails.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FAIL;
        try {
            HttpSession session = request.getSession();
            EventDTO selected_event = (EventDTO) session.getAttribute("SELECTED_EVENT");
            UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");
            JoinEventDAO jeD = new JoinEventDAO();
            FollowedEventDAO fDao = new FollowedEventDAO();
            int check = -1;
            boolean check_seat = jeD.checkSeat(selected_event, user);
            //check seat = true if there is any seat available
            if (check_seat) {
                check = jeD.checkJoinEvent(selected_event, user);
                if (check == -1) {
                    if (jeD.joinEvent(selected_event, user)) {
                        check = 1;
                        url = SUCCESS;
                    }
                } else {
                    if (jeD.updateJoinEvent(selected_event, user, check)) {
                        check = check == 1 ? 0 : 1;
                        url = SUCCESS;
                    }

                }
            }

            int follow = fDao.checkFollow(user, selected_event);
            request.setAttribute("follow", follow);
            request.setAttribute("join", check);
        } catch (Exception e) {
            log("Error at JoinEventController: " + e.toString());
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
