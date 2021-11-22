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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SE151264
 */
public class CreatePostController extends HttpServlet {

    private final String ERROR = "createPost.jsp";
    private final String SUCCESS = "editPost.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession();
        try {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String video = request.getParameter("video");
            boolean check = true;
            if (title == null || title.equals("")) {
                request.setAttribute("ERROR_TITLE", "Title must not be blank!");
                check = false;
            }
            if (content == null || content.equals("")) {
                request.setAttribute("ERROR_DESCRIPTION", "Description must not be blank!");
                check = false;
            }
            if (video == null) {
                video="";
            }
            if (check) {
                UserDTO user = (UserDTO) session.getAttribute("CURRENT_USER");
                Date createDate = Date.valueOf(LocalDate.now());
                PostDTO post= new PostDTO("", user, title, content, video, createDate, "Active");
                PostDAO pdao = new PostDAO();
                if (pdao.insertPost(post)) {
                    url = SUCCESS;
                }
            }

        } catch (Exception e) {
            log("Error at CreatePostController: " + e.toString());
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
