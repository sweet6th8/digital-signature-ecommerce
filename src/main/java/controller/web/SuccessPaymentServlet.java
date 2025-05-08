package controller.web;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import dao.CartDAO;
import dao.DBConnectionPool;
import dao.OrderDAO;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cart;
import model.CartItem;
import model.PayPalConfig;
import model.Product;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/SuccessServlet")
public class SuccessPaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");

        try (Connection connection = DBConnectionPool.getDataSource().getConnection()) {
            APIContext apiContext = PayPalConfig.getAPIContext();
            // Instantiate DAOs
            CartDAO cartDAO = new CartDAO(connection);
            ProductDAO productDAO = new ProductDAO(connection);
            OrderDAO orderDAO = new OrderDAO(connection);

            // Execute the PayPal payment
            Payment payment = new Payment().setId(paymentId);
            PaymentExecution paymentExecution = new PaymentExecution().setPayerId(payerId);
            Payment executedPayment = payment.execute(apiContext, paymentExecution);


            int userId = (int) request.getSession().getAttribute("userId");
            Cart cart = cartDAO.getCartByUserId(userId);
            List<CartItem> cartItems = cartDAO.getCartItems(cart.getCartId());


            int orderId = orderDAO.createOrder(userId, cart.getTotalPrice(), "Payed");
            for (CartItem item : cartItems) {
                Product product = item.getProduct();
                product.reduceStock(item.getQuantity());
                productDAO.updateProductStock(product);
                orderDAO.addOrderItem(orderId, item.getProductId(), item.getQuantity(), product.getPrice());
            }


            cartDAO.clearCart(cart.getCartId());


            sendConfirmationEmail(userId, executedPayment, orderId);


            request.setAttribute("payment", executedPayment);


            request.getRequestDispatcher("templates/success-payment.jsp").forward(request, response);

        } catch (PayPalRESTException e) {
            e.printStackTrace();
            response.sendRedirect("templates/error-payment.jsp"); // Redirect to error page on failure
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("templates/error-payment.jsp");
        }
    }

    /**
     * Sends a confirmation email to the user after successful payment.
     *
     * @param userId   ID of the user
     * @param payment  PayPal payment object
     * @param orderId  ID of the created order
     */
    private void sendConfirmationEmail(int userId, Payment payment, int orderId) {
        // Implementation of email sending logic (if required)
        System.out.println("Sending confirmation email...");
    }
}