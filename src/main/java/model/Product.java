package model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id; // ID sản phẩm
    private String name; // Tên sản phẩm
    private String description; // Mô tả sản phẩm
    private String photo; // URL ảnh sản phẩm
    private double price; // Giá sản phẩm
    private int stock; // Số lượng tồn kho
    private Category category; // Đối tượng Category ánh xạ từ category_id

    public Product() {
    }

    public Product(int id, String name, String description, String photo, double price, int stock, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Các phương thức tiện ích
    // Kiểm tra xem sản phẩm còn trong kho hay không
    public boolean isInStock() {
        return stock > 0;
    }

    // Giảm số lượng tồn kho
    public void reduceStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException("Not enough stock available.");
        }
        this.stock -= quantity;
    }

    // Tăng số lượng tồn kho (nếu cần)
    public void increaseStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.stock += quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category=" + category +
                '}';
    }

    public void setCategoryId(int categoryId) {
        this.category.setId(categoryId);
    }
}
