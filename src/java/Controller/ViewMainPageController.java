/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.EventDAO;
import DAO.PostDAO;
import DTO.EventDTO;
import DTO.PostDTO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ViewMainPageController", urlPatterns = {"/ViewMainPageController"})
public class ViewMainPageController extends HttpServlet {
    private final String SUCCESS = "mainPage.jsp";
    private final String FAIL = "mainPage.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FAIL;
        try {
            int pageSize_event = 6;
            int pageSize_post = 3;
            
            EventDAO eDao = new EventDAO();
            PostDAO pDao = new PostDAO();
            List<EventDTO> list_event = eDao.getListEventByPage("", 1, pageSize_event);
            List<PostDTO> list_post = pDao.getListPostByPage("", 1, pageSize_post);
            
            boolean check = true;
            if (list_event != null && !list_event.isEmpty()) {
                request.setAttribute("LIST_EVENT", list_event);
            } else {
                request.setAttribute("EVENT_MESSAGE", "No event");
                check = false;
            }
            if (list_post != null && !list_post.isEmpty()) {
                request.setAttribute("LIST_POST", list_post);
            } else {
                request.setAttribute("EVENT_MESSAGE", "No post");
                check = false;
            }
            
            if(check){
                url = SUCCESS;
            } else {
                url = FAIL;
            }
        } catch (Exception e) {
            log("Error at ViewMainPageController: " + e.toString());
            request.setAttribute("ERROR_MESSAGE", "Cannot retrieve data from database!");
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
