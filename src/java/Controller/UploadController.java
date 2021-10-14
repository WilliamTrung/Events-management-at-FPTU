/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Extension.AppDirectory;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author WilliamTrung
 */
@WebServlet(name = "UploadController", urlPatterns = {"/UploadController"})
public class UploadController extends HttpServlet {

    private final String SUCCESS = "ViewOwnedEventController";
    private final String FAIL = "fileUpload.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = FAIL;
        File file;
        int maxFileSize = 5000 * 1024;
        int maxMemSize = 5000 * 1024;
        // Verify the content type
        String contentType = request.getContentType();
        String path = AppDirectory.getAppDir();
        if (!path.equals("")) {
            //create dir based on eventId
            if ((contentType.contains("multipart/form-data"))) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                // maximum size that will be stored in memory
                factory.setSizeThreshold(maxMemSize);

                // Location to save data that is larger than maxMemSize.
                factory.setRepository(new File(path));

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload((FileItemFactory) factory);

                // maximum file size to be uploaded.
                upload.setSizeMax(maxFileSize);

                try {
                    // Parse the request to get file items.
                    List fileItems = upload.parseRequest(request);
                    String id = "-1";
                    // Process the uploaded file items
                    Iterator i = fileItems.iterator();
                    while (i.hasNext()) {
                        FileItem fi = (FileItem) i.next();

                        if (fi.isFormField()) {
                            String name = fi.getFieldName();
                            if (name.equals("id")) {
                                id = fi.getString();
                            } 
                        } else {
                            // Get the uploaded file parameters
                            /*
                            String fieldName = fi.getFieldName();
                            String fileName = fi.getName();
                            boolean isInMemory = fi.isInMemory();
                            long sizeInBytes = fi.getSize();
                             */
                            //init the file name
                            String fileName = id + ".png";//fileName.substring(fileName.indexOf("."));
                            // Write the file
                            file = new File(path + "\\" + fileName);
                            fi.write(file);
                        }
                    }
                    url = SUCCESS;
                } catch (Exception e) {
                    log("Error at UploadController: " + e.toString());
                    request.setAttribute("ERROR_MESSAGE", "Failed");
                    url = FAIL;
                }
            }
        } else {
            //out no file upload
            request.setAttribute("ERROR_MESSAGE", "Cannot upload to server! - Contact the administrator/IT support for help");
        }
        request.getRequestDispatcher(url).forward(request, response);
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
