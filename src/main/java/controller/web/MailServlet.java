package controller.web;


import Service.SendMail;
import dao.DBConnectionPool;
import dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OTPGenerator;

import javax.mail.MessagingException;
import javax.mail.Transport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet(name = "MailServlet", urlPatterns = {"/templates/Mail"})
public class MailServlet extends HttpServlet {
    private Connection connection ;
    private final static String ForgotPage = "/templates/Forgot.jsp";
    private final static String ConfirmPage = "/templates/ConfirmOTP.jsp";
    private final static String MailNotExist = "email is not exist  !";

    @Override
    public void init() throws ServletException {
        try {
            connection = DBConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(ForgotPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ form
        String email = request.getParameter("email");
        UserDAO dao = new UserDAO(connection);
        try {
            if (!dao.checkEmailExist(email)) {
                request.setAttribute("message" , MailNotExist);
                request.getRequestDispatcher(ForgotPage).forward(request, response);
            }
            else {

                try {
                    String otp = OTPGenerator.generateOTP(5);
                    HttpSession session1 = request.getSession();
                    session1.setAttribute("otp", otp);
                    session1.setAttribute("email", email);

                    SendMail service = new SendMail();
                    Transport.send(service.sendMail(email, otp));

                    request.getRequestDispatcher(ConfirmPage).forward(request, response);
                } catch (MessagingException e) {
                    response.getWriter().write("An error occurred while sending the email: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}