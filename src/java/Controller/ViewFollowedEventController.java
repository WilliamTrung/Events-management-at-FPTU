/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.EventDAO;
import DAO.FollowedEventDAO;
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
@WebServlet(name = "ViewFollowedEventController", urlPatterns = {"/ViewFollowedEventController"})
public class ViewFollowedEventController extends HttpServlet {
    private final String SUCCESS = "viewEvent.jsp";
    private final String FAIL = "viewEvent.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FAIL;
        try {
            HttpSession session = request.getSession();
            String search = request.getParameter("search");
            String tempIndex = request.getParameter("index");
            String view_mode = request.getParameter("view_mode");
            Integer sessionIndex = (Integer)session.getAttribute("index");
            int index = 1;
            if (tempIndex == null) {
                if (sessionIndex!=null) {
                    index=sessionIndex;
                }
            } else {
                if (!tempIndex.isEmpty()) {
                    index = Integer.parseInt(tempIndex);
                } 
            }
            int pageSize = 6;
            EventDAO eDao = new EventDAO();
            FollowedEventDAO fDao = new FollowedEventDAO();
            
            UserDTO user = (UserDTO)session.getAttribute("CURRENT_USER");
            int countList = fDao.getFollowingEventCount(user);
            List<EventDTO> list = eDao.getFollowedEventByPage(user,search,index,pageSize);
            int endPage = (int) Math.ceil((double) countList / pageSize);
            if (list != null && !list.isEmpty()) {
                request.setAttribute("LIST_EVENT", list);
                request.setAttribute("EVENT_MESSAGE", "Page"+index);
                request.setAttribute("Search", search);
                session.setAttribute("endPage", endPage);
                session.setAttribute("index", index);
                if (view_mode!=null) {
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
