/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.PostDAO;
import DTO.PostDTO;
import Extension.Calendar;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SE151264
 */
@WebServlet(name = "UpdatePostViewController", urlPatterns = {"/UpdatePostViewController"})
public class UpdatePostViewController extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String SUCCESS = "updatePost.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String postId = request.getParameter("postId");
            String search = request.getParameter("search");
            String temp = request.getParameter("index");
            int index = 1;
            if (temp != null && !temp.isEmpty()) {
                index = Integer.parseInt(temp);
            }
            PostDAO dao = new PostDAO();
            PostDTO post = dao.getPostById(postId);
            if (post != null) {
                session.setAttribute("SELECTED_POST", post);
                request.setAttribute("search", search);
                session.setAttribute("index", index);
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR_MESSAGE", "Error at ViewPosttDetailsController");
            }
        } catch (Exception e) {
            request.setAttribute("ERROR_MESSAGE", "Cannot retrieve selected post!");
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
