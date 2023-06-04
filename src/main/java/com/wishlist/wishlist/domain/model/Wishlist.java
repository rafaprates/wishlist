package com.wishlist.wishlist.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Wishlist {

    private String userId;
    private Set<String> productIds;

    public Wishlist(String userId) {
        this.userId = userId;
        this.productIds = new HashSet<>(20);
    }

    public Wishlist(String userId, Set<String> productIds) {
        this.userId = userId;
        this.productIds = productIds;
    }

    public boolean addProduct(String productId) {
        return this.productIds.add(productId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<String> getProductIds() {
        return productIds;
    }

    public boolean isFull() {
        return this.productIds.size() > 19;
    }

    public boolean containsProduct(String productId) {
        return this.productIds.contains(productId);
    }
}
