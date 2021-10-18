/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.EventDAO;
import DAO.LocationDAO;
import DAO.SlotDAO;
import DTO.EventDTO;
import DTO.LocationDTO;
import DTO.SlotDTO;
import DTO.UserDTO;
import Extension.AI;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
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
@WebServlet(name = "UpdateEventController", urlPatterns = {"/UpdateEventController"})
public class UpdateEventController extends HttpServlet {
    private final String SUCCESS = "UploadController";
    private final String FAIL = "UpdateEventViewController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FAIL;
        try {
            HttpSession session = request.getSession();
            String Id = request.getParameter("eventId");
            int eventId = -1;
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String locationId = request.getParameter("locationId");
            title = AI.inputVietnamese(title);
            description = AI.inputVietnamese(description);
            String[] uri = request.getParameterValues("selectedTime");
            boolean check = true;
            if (Id != null) {
                eventId = Integer.parseInt(Id);
            }
            if (title == null || title.equals("")) {
                request.setAttribute("ERROR_TITLE", "Title must not be blank!");
                check = false;
            }
            if (description == null || description.equals("")) {
                request.setAttribute("ERROR_DESCRIPTION", "Description must not be blank!");
                check = false;
            }

            EventDAO edao = new EventDAO();
            EventDTO event;
            EventDTO event_old = (EventDTO) session.getAttribute("SELECTED_EVENT");
            LocationDTO location;
            if (check) {
                //temp
                SlotDAO sDao = new SlotDAO();
                List<SlotDTO> list = sDao.getListSlots();
                //end temp
                //init
                Date startDatetime = null;
                SlotDTO startSlot = null;
                SlotDTO endSlot = null;
                if (uri != null) {
                    List<SlotDTO> slots = AI.checkChosenUpdateSlot(uri, list);
                    if (slots == null) {
                        request.setAttribute("ERROR_MESSAGE", "Event must occur at present or in future!");
                    } else {
                        if (!slots.isEmpty()) {
                            startDatetime = Date.valueOf(uri[0].substring(uri[0].indexOf("-")+1, uri[0].length()));
                            startSlot = slots.get(0);
                            endSlot = slots.get(1);
                        }
                        if (startSlot == null || endSlot == null) {
                            request.setAttribute("ERROR_MESSAGE", "Event must occur in one day!");
                            check = false;
                        }
                    }
                } else {
                    startSlot = event_old.getStartSlot();
                    endSlot = event_old.getEndSlot();
                }
                if (check) {
                    location = new LocationDAO().getLocationById(locationId);
                    if (location==null) {
                        location=event_old.getLocation();
                    }
                    if (startDatetime==null) {
                        startDatetime = event_old.getStartDatetime();
                    }
                    UserDTO user = (UserDTO)session.getAttribute("CURRENT_USER");
                    event = new EventDTO(eventId, user, title, description, location, null, startDatetime, startSlot, endSlot, "Pending");

                    if (edao.updateEvent(event)) {
                        url = SUCCESS;
                        request.setAttribute("id", eventId);
                    }
                }
            } else {
                if (locationId == null) {
                    String locationTemp = request.getParameter("locationTemp");
                    location = new LocationDAO().getLocationById(locationTemp);
                } else {
                    location = new LocationDAO().getLocationById(locationId);
                }
                String week = request.getParameter("week");
                String search = request.getParameter("search");
                String temp = request.getParameter("index");
                int index = 1;
                if (temp != null && !temp.isEmpty()) {
                    index = Integer.parseInt(temp);
                }
                event = edao.getEventById(eventId);
                request.setAttribute("week", week);
                session.setAttribute("SELECTED_EVENT", event);
                request.setAttribute("search", search);
                request.setAttribute("index", index);
                request.setAttribute("location", location);
                url = FAIL;
            }

        } catch (Exception e) {
            log("Error at UpdateEventController: " + e.toString());
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
