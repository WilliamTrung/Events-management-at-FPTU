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
import Extension.Calendar;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
@WebServlet(name = "CreateEventController", urlPatterns = {"/CreateEventController"})
public class CreateEventController extends HttpServlet {

    private final String ERROR = "createEvent.jsp";
    private final String SUCCESS = "fileUpload.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String locationId = request.getParameter("locationId");
            String[] uri = request.getParameterValues("selectedTime");
            boolean check = true;
            if (title == null || title.equals("")) {
                request.setAttribute("ERROR_TITLE", "Title must not be blank!");
                check = false;
            }
            if (description == null || description.equals("")) {
                request.setAttribute("ERROR_DESCRIPTION", "Description must not be blank!");
                check = false;
            }
            if (locationId == null || locationId.equals("")) {
                request.setAttribute("ERROR_LOCATION", "Location must be chosen!");
                check = false;
            }
            if (check) {
                //temp
                SlotDAO sDao = new SlotDAO();
                List<SlotDTO> list = sDao.getListSlots();
                //end temp
                //init
                SlotDTO startSlot = null;
                SlotDTO endSlot = null;
                check = false;
                if (uri != null) {
                    List<SlotDTO> slots = AI.checkChosenSlot(uri, list);
                    if (slots == null) {
                        request.setAttribute("ERROR_MESSAGE", "Event must occur at present or in future!");
                    } else {
                        if (!slots.isEmpty()) {
                            startSlot = slots.get(0);
                            endSlot = slots.get(1);
                        }
                        if (startSlot == null || endSlot == null) {
                            request.setAttribute("ERROR_MESSAGE", "Event must occur in one day!");
                        } else {
                            check = true;
                        }
                    }
                } else {
                    request.setAttribute("ERROR_MESSAGE", "No slot is chose!");
                }
                if (check) {
                    UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");
                    LocationDTO location = new LocationDAO().getLocationById(locationId);
                    Date createDate = Date.valueOf(LocalDate.now());
                    Calendar c = new Calendar();
                    Date startDate = c.convertToDate(uri[0].split("-")[1]);
                    EventDTO newEvent = new EventDTO(0, user, title, description, location, createDate, startDate, startSlot, endSlot, "Pending");
                    EventDAO edao = new EventDAO();
                    if (edao.createEvent(newEvent)) {
                        url = SUCCESS;
                        request.setAttribute("id", edao.getLastId());
                    }
                }
                
            }
        } catch (Exception e) {
            log("Error at CreateEventController: " + e.toString());
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
