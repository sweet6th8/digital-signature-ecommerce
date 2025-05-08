package dao;

import model.Cart;
import model.CartItem;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private Connection connection;

    public CartDAO(Connection connection) {
        this.connection = connection;
    }

    public int getTotalCart(int id) throws SQLException {
        String sql = "SELECT  count(Cart_items.cart_id) " +
                "FROM  Carts C join Cart_items on Cart_items.cart_id = C.id  " +
                "WHERE  user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return -1;
    }

    public Cart getCartByUserId(int userId) {
        String query = "SELECT * FROM carts WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int cartId = rs.getInt("id");
                Cart cart = new Cart(cartId, userId);

                // Populate cart items
                String itemQuery = "SELECT * FROM cart_items WHERE cart_id = ?";
                try (PreparedStatement itemStmt = connection.prepareStatement(itemQuery)) {
                    itemStmt.setInt(1, cartId);
                    ResultSet itemRs = itemStmt.executeQuery();
                    while (itemRs.next()) {
                        int productId = itemRs.getInt("product_id");
                        int quantity = itemRs.getInt("quantity");

                        ProductDAO productDAO = new ProductDAO(connection);
                        Product product = productDAO.getProductById(productId);
                        if (product != null) {
                            cart.addItem(product, quantity);
                        } else {
                            System.err.println("Product not found: ID = " + productId);
                        }
                    }
                }

                return cart;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log SQL exceptions
        }
        return null;
    }

    public void createCart(Cart cart) {
        String query = "INSERT INTO carts (user_id) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cart.getUserId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cart.setCartId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCart(Cart cart) throws SQLException {
        String deleteItems = "DELETE FROM cart_items WHERE cart_id = ?";
        String insertItem = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteItems)) {
            deleteStmt.setInt(1, cart.getCartId());
            deleteStmt.executeUpdate();

            try (PreparedStatement insertStmt = connection.prepareStatement(insertItem)) {
                for (CartItem item : cart.getItems().values()) {
                    insertStmt.setInt(1, cart.getCartId());
                    insertStmt.setInt(2, item.getProduct().getId());
                    insertStmt.setInt(3, item.getQuantity());
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Log lỗi khi đóng kết nối thất bại
            }
        }
    }
    public List<CartItem> getCartItems(int cartId) {
        String query = "SELECT * FROM cart_items WHERE cart_id = ?";
        List<CartItem> items = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();

            ProductDAO productDAO = new ProductDAO(connection);
            // Fetch product details for each cart item
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");

                // Fetch the product
                Product product = productDAO.getProductById(productId);
                if (product != null) {
                    items.add(new CartItem(product, quantity));
                } else {
                    System.err.println("Product not found for ID: " + productId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void clearCart(int cartId) {
        String query = "DELETE FROM cart_items WHERE cart_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
