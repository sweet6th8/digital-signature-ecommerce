package controller.web;

import Util.GeneratePassword;
import dao.CartDAO;
import dao.DBConnectionPool;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "Login", urlPatterns = {"/templates/Login"})
public class LoginServlet extends HttpServlet {
    private Connection connection;
    private final static String loginPage = "/templates/login.jsp";
    private final static String ErrorMail = "Email is not exist!";
    private final static String ErrorActive = "Account is not active!";
    private final static String ErrorPassword = "Incorrect account password information";

    @Override
    public void init() throws ServletException {
        try {
            connection = DBConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) resp.sendRedirect(getServletContext().getContextPath() + "/");
        else req.getRequestDispatcher(req.getContextPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        UserDAO udao = new UserDAO(connection);
        HttpSession session = req.getSession();
        String message = null;
        String realPass = null;
        try {
            realPass = udao.getPassword(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (!udao.checkEmailExist(email)) {message = ErrorMail;}
             else if (!udao.checkActive(email)) {message = ErrorActive;}
            else {message = (!GeneratePassword.checkPassword(pass, realPass)) ?  ErrorPassword :  null ;}
            if (message != null) {
                req.setAttribute("message", message);
                req.getRequestDispatcher(loginPage).forward(req, resp);
            }
            User user = udao.getLogin(email, realPass);

            int userId = user.getId();
            String role = user.getRole();
            session.setAttribute("role", role);
            if (role.equals("Admin")) resp.sendRedirect(getServletContext().getContextPath() + "/secure/admin");
            else {
                CartDAO cartDAO = new CartDAO(connection);
                int total = cartDAO.getTotalCart(userId);
                session.setAttribute("count", total);
                session.setAttribute("userId", userId);
                session.setAttribute("Img", user.getImage());
                resp.sendRedirect(req.getContextPath());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
}
