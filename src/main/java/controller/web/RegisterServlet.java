package controller.web;

import Service.SendMail;
import Util.CreateUser;
import dao.CartDAO;
import dao.DBConnectionPool;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cart;
import model.User;

import javax.mail.MessagingException;
import javax.mail.Transport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/templates/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
    public static final String ERROR_MESSAGE = "Register error";
    public static final String SUCCESS = "Registration successful, please check your email to activate your account!";
    public static final String EMAIL_EXISTS = "Email already exists!";
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = DBConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // call user dao
        UserDAO userDAO = new UserDAO(connection);
        CartDAO cartDAO = new CartDAO(connection);
        User user = CreateUser.createUser(req, resp);

        if (user == null) {
            req.setAttribute("message", ERROR_MESSAGE);
            req.getRequestDispatcher("/templates/login.jsp").forward(req, resp);
            return;
        }
        try {
            if (userDAO.checkEmailExist(user.getEmail())) {
                req.setAttribute("message", EMAIL_EXISTS);
                req.getRequestDispatcher("/templates/register.jsp").forward(req, resp);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        try {
            if (userDAO.registerUser(user)) {
                int id = userDAO.getUserId(user.getEmail());
                cartDAO.createCart(new Cart(id));
                SendMail service = new SendMail();
                Transport.send(service.activeAcount(user.getEmail(), id));
                req.setAttribute("success", SUCCESS);
                req.getRequestDispatcher("/templates/login.jsp").forward(req, resp);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
        resp.sendRedirect(req.getContextPath() + "/templates/register.jsp");

    }

}

