package dao;

import model.HistoryProduct;
import model.OrderItem;
import model.OrderProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    private Connection conn;
    private static final String query = "SELECT P.id , P.name , OI.price, OI.quantity , P.photo,O.status  FROM " +
            "Order_Items OI JOIN    Orders O on O.id = OI.order_id" +
            " JOIN product P ON P.id = OI.product_id" +
            " WHERE O.user_id =  ?";
    private static final String SQL_SELECT_ORDER = "SELECT O.id , KH.fullName  , kh.phone , kh.address , O.created_at , O.total_price ,O.status  FROM \n" +
            "            Order_Items OI JOIN    Orders O on O.id = OI.order_id\n" +
            "             JOIN product P ON P.id = OI.product_id \n" +
            "\t\t\t JOIN [dbo].[User] KH ON KH.id = O.user_id\n";
    public OrderItemDAO(Connection conn) {
        this.conn = conn;
    }

    public List<HistoryProduct> getAllHistoryProducts(int userId) throws SQLException {

        List<HistoryProduct> historyProducts = new ArrayList<HistoryProduct>();

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            HistoryProduct hp = new HistoryProduct(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4),
                    rs.getString(5),
                    rs.getString(6)

            );
            historyProducts.add(hp);

        }

        return historyProducts;
    }

    public List<OrderProduct> getAllProducts() throws SQLException {
        List<OrderProduct> result = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ORDER);
       ResultSet rs =  ps.executeQuery();
        while (rs.next()) {
            OrderProduct od = new OrderProduct(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getInt(6),
                    rs.getString(7)
            );
            result.add(od);

        }
        return result;
    }

}
