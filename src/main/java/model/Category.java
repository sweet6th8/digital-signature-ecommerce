package model;


import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String title;
    private String description;
    private List<Product> products;

    public Category(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        products = new ArrayList<>();

    }

    public Category(int id, String title){
        this.id = id;
        this.title = title;
    }

    public Category(){

    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
