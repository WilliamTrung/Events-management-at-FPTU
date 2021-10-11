/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.EventDAO;
import DTO.EventDTO;
import DTO.UserDTO;
import java.io.IOException;
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
@WebServlet(name = "ViewOwnedEventController", urlPatterns = {"/ViewOwnedEventController"})
public class ViewOwnedEventController extends HttpServlet {

    private final String FAIL = "viewOwnEvent.jsp";
    private final String SUCCESS = "viewOwnEvent.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FAIL;
        HttpSession session = request.getSession();
        try {
            String search = request.getParameter("search");
            Integer indexSession = (Integer) session.getAttribute("index");
            String indexRequest = request.getParameter("index");
            int index = 1;
            if (indexRequest != null && !indexRequest.isEmpty()) {
                index = Integer.parseInt(indexRequest);
            } else if (indexSession != null) {
                index = indexSession;
            }
            int pageSize = 6;
            EventDAO dao = new EventDAO();
            UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");
            List<EventDTO> list = dao.getListEventByPageByOwner(user, search, index, pageSize);
            if (list != null && !list.isEmpty()) {
                int countList = new EventDAO().countListOwnedEvent(user);
                int endPage = (int) Math.ceil((double) countList / pageSize);
                session.setAttribute("endPage", endPage);
                request.setAttribute("LIST_EVENT", list);
                request.setAttribute("EVENT_MESSAGE", "Page" + index);
                session.setAttribute("index", index);
                request.setAttribute("Search", search);
                url = SUCCESS;
            } else {
                request.setAttribute("EVENT_MESSAGE", "No event found!");
            }
        } catch (Exception e) {
            log("Error at ViewEventController: " + e.toString());
            request.setAttribute("ERROR_MESSAGE", "Cannot retrieve events' information!");
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
