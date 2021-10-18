/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.LocationDAO;
import DTO.LocationDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author WilliamTrung
 */
@WebServlet(name = "ViewLocationController", urlPatterns = {"/ViewLocationController"})
public class ViewLocationController extends HttpServlet {

    private final String SUCCESS = "locationManagement.jsp";
    private final String FAIL = "locationManagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FAIL;
        try {
            String search = request.getParameter("Search");
            String index_temp = request.getParameter("index");
            int index = 1;
            if (index_temp != null && !index_temp.isEmpty()) {
                index = Integer.parseInt(index_temp);
            }
            LocationDAO lDao = new LocationDAO();
            int pageSize = 10;
            int location_count = lDao.getLocationEndPage();
            int location_end_page = (int) Math.ceil((double) location_count / pageSize);
            List<LocationDTO> list = lDao.getListLocationByPage(search, index, pageSize);
            if (list != null && !list.isEmpty()) {
                request.setAttribute("location_end_page", location_end_page);
                request.setAttribute("LIST_LOCATION", list);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at ViewLocationController: " + e.toString());
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
