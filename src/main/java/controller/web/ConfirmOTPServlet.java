package controller.web;


import dao.DBConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "ConfirmOTPServlet", urlPatterns = {"/templates/ConfirmOTPServlet"})
public class ConfirmOTPServlet extends HttpServlet {
    Connection con;

    @Override
    public void init() throws ServletException {
        try {
            con = DBConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        request.getRequestDispatcher("/templates/forgetPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String otp = request.getParameter("otp");
        HttpSession session = request.getSession();
        String sessionOTP = (String) session.getAttribute("otp");
        String navigate = otp.equalsIgnoreCase(sessionOTP) ? "/templates/CreateNewPassword.jsp" : "/templates/forgetPassword.jsp";
        request.getRequestDispatcher(navigate).forward(request, response);

    }
}