package model;

public class OrderItem {
    private int id; // ID mục đơn hàng
    private int orderId; // ID của đơn hàng mà mục này thuộc về
    private Product product; // Sản phẩm
    private int quantity; // Số lượng sản phẩm
    private double price; // Giá sản phẩm tại thời điểm đặt hàng

    // Constructor
    public OrderItem() {}

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductId() {
        return product.getId();
    }

    public void setProductId(int productId) {
        this.product.setId(productId);
    }
}
