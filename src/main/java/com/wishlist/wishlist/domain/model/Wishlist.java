package com.wishlist.wishlist.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Wishlist {

    private String userId;
    private Set<Product> products;

    public Wishlist(String userId) {
        this.userId = userId;
        this.products = new HashSet<>(20);
    }

    public Wishlist(String userId, Set<Product> products) {
        this.userId = userId;
        this.products = products;
    }

    public boolean addProduct(Product product) {
        return this.products.add(product);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public boolean isFull() {
        return this.products.size() > 19;
    }

    public boolean containsProduct(String productId) {
        return products.stream()
                .anyMatch(product -> product.getProductId().equals(productId));
    }
}
