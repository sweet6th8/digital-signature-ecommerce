package dao;

import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SavedDao {
    private Connection conn;
    private HashMap<Integer, Product> map;
    private final static String SQL_INSERT_SAVED = "INSERT INTO  Saved_Items VALUES (?,?,?)";
    private final static String SQL_SELECT_ALL = "\n" +
            "SELECT P.id , P.name , P.price  , P.photo   FROM Saved_Items S join Product P on P.id=S.product_id  where user_id = ?";
    public SavedDao(Connection conn) {
        this.conn = conn;
        this.map = new HashMap<>();
    }
    public boolean addProduct (int userId , int productId , int quantity) throws SQLException {
        if (map.containsKey(productId)) {
            return false;
        }
        else {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT_SAVED);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            return ps.executeUpdate()>0;
        }
    }
    public List<Product> getAllProduct (int userID ) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);

        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getDouble("price"));
            p.setPhoto(rs.getString("photo"));
            this.map.put(p.getId(), p);
        }
        return map.values().stream().toList();
    }
}
