package controller.web;


import dao.CartDAO;
import dao.DBConnectionPool;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Product;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/secure/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String LOGIN_PAGE = "/templates/LoginServlet.jsp";
    private static final String CART_PAGE = "/templates/User/cart.jsp";
    private static final String ERROR_INVALID_INPUT = "Invalid input.";
    private static final String ERROR_PROCESSING_REQUEST = "An error occurred while processing your request.";
    private static final String ERROR_PRODUCT_NOT_FOUND = "Product not found.";
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        dataSource = DBConnectionPool.getDataSource();
        if (dataSource == null) {
            throw new ServletException("Failed to initialize DataSource.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = getUserIdFromSession(request.getSession());
        if (isUserNotLoggedIn(userId)) {
            redirectToPage(response, LOGIN_PAGE);
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            CartDAO cartDAO = new CartDAO(connection);

            // Display the user's cart
            handleCartDisplay(request, response, userId, cartDAO);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while displaying cart: " + e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ERROR_PROCESSING_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = getUserIdFromSession(request.getSession());
        if (isUserNotLoggedIn(userId)) {
            redirectToPage(response, LOGIN_PAGE);
            return;
        }


        String action = request.getParameter("action");

        if (isInvalidInput(action)) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            CartDAO cartDAO = new CartDAO(connection);

            switch (action) {
                case "addToCart": // Added new case
                    ProductDAO productDAO = new ProductDAO(connection); // Dependency required for handleAddToCart
                    handleAddToCart(request, response, userId, cartDAO, productDAO);
                    break;
                case "updateQuantity":
                    updateQuantity(request, response, userId, cartDAO);
                    break;
                case "removeItem":
                    removeItem(request, response, userId, cartDAO);
                    break;
                default:
                    sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Unsupported action.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
        }
    }

    private void handleCartDisplay(HttpServletRequest request, HttpServletResponse response, int userId, CartDAO cartDAO) throws ServletException, IOException {
        Cart cart = cartDAO.getCartByUserId(userId);

        if (cart == null || cart.getItems().isEmpty()) {
            request.setAttribute("errorMessage", "Your cart is empty.");
            request.getSession().setAttribute("cart", null);
        } else {
            request.getSession().setAttribute("cart", cart);
        }

        // Forward to the cart JSP page for rendering
        request.getRequestDispatcher(CART_PAGE).forward(request, response);
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response, int userId, CartDAO cartDAO, ProductDAO productDAO) throws IOException {
        String productIdParam = request.getParameter("productId");
        String quantityParam = request.getParameter("quantity");


        if (isInvalidInput(productIdParam) || isInvalidInput(quantityParam)) {

            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, ERROR_INVALID_INPUT);
            return;
        }

        try {
            int productId = Integer.parseInt(productIdParam);
            int quantity = Integer.parseInt(quantityParam);

            validateProductQuantity(quantity, response);

            Product product = productDAO.getProductById(productId);
            if (product == null) {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, ERROR_PRODUCT_NOT_FOUND);
                return;
            }

            Cart cart = addItemToCart(userId, cartDAO, product, quantity);
            request.getSession().setAttribute("cart", cart);

            redirectToPage(response, "/secure/cart");
        } catch (NumberFormatException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, ERROR_INVALID_INPUT);
        } catch (SQLException e) {
            e.printStackTrace();
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ERROR_PROCESSING_REQUEST);
        }
    }

    private Integer getUserIdFromSession(HttpSession session) {
        return (Integer) session.getAttribute("userId");
    }

    private boolean isUserNotLoggedIn(Integer userId) {
        return userId == null || userId <= 0;
    }

    private boolean isInvalidInput(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void redirectToPage(HttpServletResponse response, String page) throws IOException {
        // Get the context path dynamically
        String contextPath = getServletContext().getContextPath();
        // Prepend the context path to the redirect URL
        response.sendRedirect(contextPath + page);
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.getWriter().write(message);
    }

    private void validateProductQuantity(int quantity, HttpServletResponse response) throws IOException {
        if (quantity <= 0) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid quantity.");
            throw new IllegalArgumentException("Invalid quantity");
        }
    }

    private Cart addItemToCart(int userId, CartDAO cartDAO, Product product, int quantity) throws SQLException {
        Cart cart = cartDAO.getCartByUserId(userId);
        if (cart == null) {
            cart = new Cart(0, userId); // Default cartId is 0 temporarily
            cartDAO.createCart(cart);
        }

        cart.addItem(product, quantity);
        cartDAO.updateCart(cart);
        return cart;
    }

    private void updateQuantity(HttpServletRequest request, HttpServletResponse response, int userId, CartDAO cartDAO) throws IOException {
        String productIdParam = request.getParameter("productId");
        String quantityParam = request.getParameter("quantity");

        if (isInvalidInput(productIdParam) || isInvalidInput(quantityParam)) {
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid input.\"}");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdParam);
            int quantity = Integer.parseInt(quantityParam);

            if (quantity <= 0) {
                response.getWriter().write("{\"success\": false, \"message\": \"Invalid quantity.\"}");
                return;
            }

            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart == null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Cart not found.\"}");
                return;
            }

            // Update quantity in the cart
            cart.updateQuantity(productId, quantity);
            cartDAO.updateCart(cart);
            double itemTotalPrice = cart.getItemTotalPrice(productId); // Ensure Cart class has this function
            double totalCartPrice = cart.getTotalPrice();
            HttpSession session = request.getSession();
            session.setAttribute("cart", cart);

            // Respond with both the updated individual item price and total price
            response.getWriter().write("{\"success\": true, \"updatedItemTotal\": " + itemTotalPrice + ", \"totalCartPrice\": " + totalCartPrice + "}");
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid product ID or quantity.\"}");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeItem(HttpServletRequest request, HttpServletResponse response, int userId, CartDAO cartDAO) throws IOException {
        String productIdParam = request.getParameter("productId");

        if (isInvalidInput(productIdParam)) {
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid product ID.\"}");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdParam);

            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart == null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Cart not found.\"}");
                return;
            }

            // Remove item from the cart
            cart.removeItem(productId);
            cartDAO.updateCart(cart);
            double totalCartPrice = cart.getTotalPrice();
            request.getSession().setAttribute("cart", cart);
            // Respond with updated total price
            response.getWriter().write("{\"success\": true, \"totalCartPrice\": " + totalCartPrice + "}");
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid product ID.\"}");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}