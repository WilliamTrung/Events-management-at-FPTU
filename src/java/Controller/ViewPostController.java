/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.PostDAO;
import DTO.PostDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SE151264
 */
public class ViewPostController extends HttpServlet {

    private final String ERROR = "viewPost.jsp";
    private final String SUCCESS = "viewPost.jsp";
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
        try {
            HttpSession session = request.getSession();
            String search = request.getParameter("search");
            String tempIndex = request.getParameter("index");
            int index = 1;
            if (tempIndex!=null && !tempIndex.isEmpty()) {
                index = Integer.parseInt(tempIndex);
            }
            int pageSize = 6;
            PostDAO pDao = new PostDAO();
            List<PostDTO> list = pDao.getListPostByPage(search, index, pageSize);
            int listsize = pDao.countPost();
            int endpagePost = (int) Math.ceil( (double)listsize/pageSize);
            session.setAttribute("endPagePost", endpagePost);
            if(list!=null && !list.isEmpty()){
                request.setAttribute("LIST_POST", list);
                url=SUCCESS;
            }else{
                request.setAttribute("POSTMESSAGE", "List is empty");
            }
        } catch (Exception e) {
            log("Error at ViewPostController: " + e.toString());
            request.setAttribute("ERROR_MESSAGE", "Cannot retrieve posts' information!");
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
