package vn.fpt.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.fpt.dal.CategoryDAO;
import vn.fpt.dal.UserDAO;
import vn.fpt.entity.Category;
import vn.fpt.entity.Role;
import vn.fpt.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "UserLoginServlet", urlPatterns = "/login")
public class UserLoginServlet extends HttpServlet {
    private UserDAO userDAO = UserDAO.getInstance();

    private CategoryDAO categoryDAO = CategoryDAO.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryDAO.findAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("login?error=emptyInput");
            return;
        }

        HttpSession session = request.getSession(false);

        User user = userDAO.findByUsername(username);


        if ("INACTIVE".equals(user.getStatus()) || user.getStatus() == null) {
            response.sendRedirect("login?error=lockedAccount");
            return;
        }

        boolean isUser = false;
        Set<Role> roles = user.getRole();
        if (roles != null) {
            for (Role role : roles) {
                if ("user".equals(role.getRoleName())) {
                    isUser = true;
                    break;
                }
            }
        }


        if(user != null && isUser) {
            session.setAttribute("user", user);

            response.sendRedirect("home");
        }else {
            response.sendRedirect("login?success=false");
        }


    }
}
