package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import GoogleAPI.GooglePojo;
import GoogleAPI.GoogleUtils;


@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final String SUCCESS = "index.jsp";
    private final String FAIL = "login.jsp";

    public LoginGoogleServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String url = FAIL;
        if (code == null || code.isEmpty()) {
            url = FAIL;
        } else {

            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
            String id = googlePojo.getId();
            String name = googlePojo.getName();

            String givenName = googlePojo.getGiven_name();
            String oName = googlePojo.getFamily_name();
            String email = googlePojo.getEmail();          

            request.setAttribute("id", id);
            request.setAttribute("gName", givenName);
            request.setAttribute("fName", oName);
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            url = SUCCESS;
        }
        request.getRequestDispatcher(url).forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
