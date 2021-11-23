/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.PostDAO;
import DTO.PostDTO;
import DTO.UserDTO;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
@WebServlet(name = "UpdatePostController", urlPatterns = {"/UpdatePostController"})
public class UpdatePostController extends HttpServlet {

    private final String SUCCESS = "ViewOwnedPostController";
    private final String FAIL = "UpdatePostViewController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FAIL;
        try {
            HttpSession session = request.getSession();
            String postId = request.getParameter("postId");
            String title = request.getParameter("title");
            String description = request.getParameter("content");
            String search = request.getParameter("search");
            String temp = request.getParameter("index");
            int index = 1;
            if (temp != null && !temp.isEmpty()) {
                index = Integer.parseInt(temp);
            }
            boolean check = true;
            if (title == null || title.equals("")) {
                request.setAttribute("ERROR_TITLE", "Title must not be blank!");
                check = false;
            }
            if (description == null || description.equals("")) {
                request.setAttribute("ERROR_DESCRIPTION", "Content must not be blank!");
                check = false;
            }

            PostDAO dao = new PostDAO();
            PostDTO post=null;
            PostDTO post_old = dao.getPostById(postId);
            Date createDate = Date.valueOf(LocalDate.now());
            if (check) {
                UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");
                post = new PostDTO(postId, user, title, description, "", createDate, "Active");
                if(dao.updatePost(post)){
                    url=SUCCESS;
                }
            }else{
                post=post_old;
                url=FAIL;
            }
            request.setAttribute("search", search);
            request.setAttribute("index", index);
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
