package dao;

import java.sql.*;

public class CartItemDAO {
    private Connection connection;

    public CartItemDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addCartItem(int cartId, int productId, int quantity, double price) {
        String query = "INSERT INTO Cart_items (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }


            if (connection == null || connection.isClosed()) {
                throw new SQLException("Connection is null or closed");
            }


            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);


            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
