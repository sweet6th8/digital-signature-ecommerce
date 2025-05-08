package controller.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;

import java.io.IOException;

@WebServlet("/secure/place-order")
public class PlaceOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart"); // Cart sẽ chứa các item trong giỏ hàng của người dùng

        if (cart == null || cart.getItems().isEmpty()) {
            request.setAttribute("errorMessage", "Your cart is empty. Please add items before placing an order.");
            request.getRequestDispatcher("/templates/placeOrder.jsp").forward(request, response);
            return;
        }

        request.setAttribute("cartItems", cart.getItems().values());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/templates/placeOrder.jsp");
        dispatcher.forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin người dùng từ form (contact info, delivery info, payment info)
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        String state = request.getParameter("state");
        String street = request.getParameter("street");
        String postalCode = request.getParameter("postalCode");

        // Logic xử lý đơn hàng (kiểm tra thanh toán, lưu trữ đơn hàng, v.v.)
        // Sau khi xử lý đơn hàng, chuyển hướng đến trang xác nhận
        response.sendRedirect("order-confirmation.jsp");
    }
}
