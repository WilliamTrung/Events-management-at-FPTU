/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.EventDAO;
import DTO.EventDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class ViewEventController extends HttpServlet {

    private final String ERROR = "viewEvent.jsp";
    private final String SUCCESS = "viewEvent.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String search = request.getParameter("search");
            String tempIndex = request.getParameter("index");
            String view_mode = request.getParameter("view_mode");
            int index = 1;
            if (tempIndex != null && !tempIndex.isEmpty()) {
                index = Integer.parseInt(tempIndex);
            }
            int pageSize = 6;
            EventDAO eDao = new EventDAO();
            List<EventDTO> list = eDao.getListEventByPage(search, index, pageSize);
            if (list != null && !list.isEmpty()) {
                int countList = eDao.countListEvent();
                int endPage = (int) Math.ceil(((double) countList / pageSize));
                session.setAttribute("endPage", endPage);
                request.setAttribute("LIST_EVENT", list);
                request.setAttribute("EVENT_MESSAGE", "Page" + index);
                request.setAttribute("Search", search);
                session.setAttribute("index", index);
                if (view_mode != null) {
                    session.setAttribute("view_mode", view_mode);
                }               
                url = SUCCESS;
            } else {
                request.setAttribute("EVENT_MESSAGE", "No event");
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
