package Controller;

import DAO.EventDAO;
import DAO.SlotDAO;
import DAO.UserDAO;
import DTO.EventDTO;
import DTO.SlotDTO;
import DTO.UserDTO;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import GoogleAPI.GoogleUtils;
import java.util.List;
import javax.servlet.http.HttpSession;

@WebServlet("/login-google")
public class LoginGoogleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final String SUCCESS = "ViewMainPageController";
    private final String FAIL = "login.jsp";

    public LoginGoogleServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code;
        String url = FAIL;
        try {
            int pageSize=6;
            code = request.getParameter("code");
            if (code == null || code.isEmpty()) {
                url = FAIL;
            } else {
                String accessToken = GoogleUtils.getToken(code);
                UserDTO user = GoogleUtils.getUserInfo(accessToken);
                UserDAO ud = new UserDAO();
                //check condition if email is valid and is fpt email
                //user.getHd()==null when the email's tail is @gmail.com
                if (user.getHd() != null) {
                    if (user.isVerified_email() == true && user.getHd().equals("fpt.edu.vn")) {
                        user = ud.loginUser(user);
                        if (user != null) {
                            HttpSession session = request.getSession();
                            
                            EventDAO eDao = new EventDAO();
                            
                            List<EventDTO> list_event = eDao.getListEventByPage("", 1, pageSize);
                            List<SlotDTO> list_slot = new SlotDAO().getListSlots();

                            //check if login user is an admin
                            if (user.getRole().equals("Admin")) {
                                session.setAttribute("MODE", "USER_MODE");
                            }
                            session.setAttribute("CURRENT_USER", user);                           
                            
                            request.setAttribute("LIST_EVENT", list_event);
                            request.setAttribute("LIST_SLOT", list_slot);
                            session.setAttribute("index", 1);
                            session.setAttribute("view_mode", "normal");
                            int countList = eDao.countListEvent();
                            int endPage = (int) Math.ceil( ((double) countList/pageSize));
                            session.setAttribute("endPage", endPage);
                            url = SUCCESS;
                        } else {
                            request.setAttribute("LOGIN_ERROR", "Cannot retrieve user's information!");
                            url = FAIL;
                        }
                    } else {
                        request.setAttribute("LOGIN_ERROR", "Email is not valid!");
                        url = FAIL;
                    }
                } else {
                    request.setAttribute("LOGIN_ERROR", "Email is not valid!");
                    url = FAIL;
                }
            }
        } catch (Exception e) {
            request.setAttribute("SYSTEM_ERROR", e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
