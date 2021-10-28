/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.CommentDAO;
import DAO.PostDAO;
import DTO.CommentDTO;
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
public class ViewPostDetailsController extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String SUCCESS = "viewPostDetails.jsp";

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
            String postId = request.getParameter("postId");
            String search = request.getParameter("search");
            String tempIndex = request.getParameter("index");
            String countListCmtString = request.getParameter("countListCmt");
	    int countListCmt=0;
            if(countListCmtString!=null){
                countListCmt= Integer.parseInt(countListCmtString);
            }
            int indexCmt=1;
            int index = 1;
            if (tempIndex!=null && !tempIndex.isEmpty()) {
                index = Integer.parseInt(tempIndex);
            }
            PostDAO pDao = new PostDAO();
            PostDTO post = pDao.getPostById(postId);
CommentDAO cdao = new CommentDAO();
            if(countListCmt>=10 && countListCmt<=cdao.countPostComment(postId)){
                indexCmt=countListCmt/10;
                session.setAttribute("END_OF_COMMENT", true);
            }else{
                session.setAttribute("END_OF_COMMENT", false);
            }
            List<CommentDTO> listShortComment= cdao.getShortListPostComment(postId, indexCmt, 10);
            if (post != null) {
                List<String> video = Extension.AI.detectEmbededLinks(post.getVideo());
                request.setAttribute("LIST_VIDEO", video);
                session.setAttribute("SELECTED_POST", post);
                request.setAttribute("Search", search);
                request.setAttribute("index", index);
                session.setAttribute("LIST_COMMENT", listShortComment);
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR_MESSAGE", "Error at ViewPostDetailsController");
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
