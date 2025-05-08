package controller.web;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/CancelServlet")
public class CancelPaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Set a session attribute to indicate cancellation
        session.setAttribute("paymentMessage", "Payment process was canceled.");
        // Redirect to a cancellation or cart page
        response.sendRedirect("templates/cancel.jsp"); // Adjust the path based on your project structure
    }
}