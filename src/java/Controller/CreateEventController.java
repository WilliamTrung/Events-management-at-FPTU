/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.EventDAO;
import DAO.LocationDAO;
import DAO.UserDAO;
import DTO.EventDTO;
import DTO.LocationDTO;
import DTO.UserDTO;
import java.awt.Event;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
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
@WebServlet(name = "CreateEventController", urlPatterns = {"/CreateEventController"})
public class CreateEventController extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String SUCCESS = "createEvent.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String locationId = request.getParameter("locationId");
            String startDatetime = request.getParameter("startDatetime");
            Date startDate = Date.valueOf(startDatetime);
            String endDatetime = request.getParameter("endDatetime");
            Date endDate = Date.valueOf(endDatetime);
            String picture = request.getParameter("picture");
            
            UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");
            LocationDAO ldao = new LocationDAO();
            LocationDTO location = ldao.getLocationById(locationId);
            Date createDate = Date.valueOf(LocalDateTime.now().toLocalDate());
            EventDTO newEvent = new EventDTO(0, user, title, description, location, createDate, startDate, endDate, "", picture);
            EventDAO edao = new EventDAO();
            if (edao.createEvent(newEvent)) {
                url=SUCCESS;
            }
        } catch (Exception e) {
            log("Error at CreateEventController: "+e.toString());
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
