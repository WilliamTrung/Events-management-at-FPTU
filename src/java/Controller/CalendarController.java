/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.EventDAO;
import DAO.LocationDAO;
import DTO.EventDTO;
import DTO.LocationDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
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
@WebServlet(name = "CalendarController", urlPatterns = {"/CalendarController"})
public class CalendarController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        final String FAIL = request.getParameter("where");
        final String SUCCESS = request.getParameter("where");
        String url = FAIL;
        try {
            HttpSession session = request.getSession();
            String weekChange = request.getParameter("weekChange");
            String temp = request.getParameter("week");
            EventDTO event = (EventDTO) session.getAttribute("SELECTED_EVENT");
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String locationId = request.getParameter("locationId");

            LocationDTO location;
            if (locationId == null) {
                location = event.getLocation();
            } else {
                location = new LocationDAO().getLocationById(locationId);
            }
            if (event != null) {
                event.setTitle(title);
                event.setDescription(description);
                event.setLocation(location);
            } else {
                request.setAttribute("title", title);
                request.setAttribute("description", description);
                request.setAttribute("location", location);
            }
            int week = 0;
            if (temp != null) {
                week = Integer.parseInt(temp);
            }
            if (weekChange.equals("-")) {
                if (week > 0) {
                    week--;
                }
            } else {
                week++;
            }

            session.setAttribute("SELECTED_EVENT", event);
            request.setAttribute("week", week);
            url = SUCCESS;
        } catch (Exception e) {
            e.toString();
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
