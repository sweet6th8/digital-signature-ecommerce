package model;

public class Payment {
    private int id; // ID thanh toán
    private int orderId; // ID của đơn hàng
    private double amount; // Số tiền thanh toán
    private String paymentMethod; // Phương thức thanh toán (thẻ tín dụng, PayPal, v.v.)
    private String status; // Trạng thái thanh toán

    // Constructor
    public Payment() {}

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
