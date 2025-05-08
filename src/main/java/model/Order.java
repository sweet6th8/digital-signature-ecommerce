package model;

import java.util.Date;
import java.util.List;

public class Order {
    private int id; // ID đơn hàng
    private int userId; // ID người dùng
    private double totalAmount; // Tổng số tiền
    private String status; // Trạng thái đơn hàng
    private Date orderDate; // Ngày đặt hàng
    private String shippingAddress; // Địa chỉ giao hàng
    private List<OrderItem> items; // Danh sách các mục trong đơn hàng
    // Constructor
    public Order() {}

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

//    public String getPaymentMethod() {
//        // Lấy phương thức thanh toán từ đơn hàng
//        return "Thanh toán qua đại lý";
//    }
}
