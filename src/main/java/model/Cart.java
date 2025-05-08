package model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {
    public static final int UNASSIGNED_CART_ID = -1;
    private int cartId;
    private int userId;
    private Map<Integer, CartItem> items; // Product ID -> CartItem map

    // Constructor
    public Cart(int cartId, int userId) {
        this.cartId = cartId;
        this.userId = userId;
        this.items = new ConcurrentHashMap<>();
    }

    public Cart(int userId) {
        this.userId = userId;
        this.items = new ConcurrentHashMap<>();
    }

    // Create an unassigned cart (e.g., for a guest user)
    public static Cart createUnassignedCart(int userId) {
        return new Cart(UNASSIGNED_CART_ID, userId);
    }

    // Add an item to the cart
    public synchronized void addItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        // Check stock availability and update the cart
        items.compute(product.getId(), (productId, existingItem) -> {
            if (existingItem == null) {
                // Validate initial quantity
                if (quantity > product.getStock()) {
                    throw new IllegalArgumentException("Insufficient stock. Requested: " + quantity + ", Available: " + product.getStock());
                }
                return new CartItem(product, quantity);
            }

            // Calculate the new quantity
            int newQuantity = existingItem.getQuantity() + quantity;

            // Validate new quantity against stock
            if (newQuantity > product.getStock()) {
                throw new IllegalArgumentException("Insufficient stock. Requested: " + newQuantity + ", Available: " + product.getStock());
            }

            // Update existing item
            existingItem.setQuantity(newQuantity);
            items.put(product.getId(), existingItem);
            return existingItem;
        });
    }

    // Update the quantity of an item in the cart
    public synchronized void updateQuantity(int productId, int quantity) {
        CartItem item = items.get(productId);
        if (item == null) {
            throw new IllegalArgumentException("Product not found in the cart.");
        }
        if (quantity <= 0 || quantity > item.getProduct().getStock()) {
            throw new IllegalArgumentException("Invalid quantity.");
        }

        item.setQuantity(quantity);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", items=" + items +
                '}';
    }

    // Remove an item from the cart
    public synchronized void removeItem(int productId) {
        items.remove(productId);
    }

    // Calculate the total price for the cart
    public double calculateTotal() {
        return items.values().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    // Get the total price of a specific product
    public double getItemTotalPrice(int productId) {
        CartItem item = items.get(productId);
        return (item != null) ? item.getTotalPrice() : 0.0;
    }

    // Getters and setters for cart-specific data
    public int getCartId() {
        return cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return calculateTotal();
    }

    // Clear all items from the cart
    public void clearCart() {
        items.clear();
    }

    // Check if a product exists in the cart
    public boolean containsProduct(int productId) {
        return items.containsKey(productId);
    }

    public int getId() {
        return this.cartId;
    }
}