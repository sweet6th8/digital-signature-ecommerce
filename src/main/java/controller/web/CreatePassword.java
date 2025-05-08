package controller.web;


import Util.GeneratePassword;
import dao.DBConnectionPool;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/templates/CreatePassword"})
public class CreatePassword extends HttpServlet {
   private  Connection con ;
   private final static String loginPage ="/templates/login.jsp";
   private final static String CreatePasswordPage ="/templates/CreatePassword.jsp";
   private final static String SuccesMesage = "Your new password has been updated successfully";
    @Override
    public void init() throws ServletException {
        try {
            con = DBConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO da = new UserDAO(con);
        String password = req.getParameter("password");
        String checkpassword = req.getParameter("Checkpassword");
        if (!checkpassword.equals(password)) {
            resp.sendRedirect(req.getContextPath() + CreatePasswordPage);
        }
        else {
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");
            try {
                String newPassword = GeneratePassword.hashPassword(password);
                da.updatePassword(email,newPassword);
                req.setAttribute("success",SuccesMesage);
                req.getRequestDispatcher( loginPage).forward(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
