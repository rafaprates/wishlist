package com.wishlist.wishlist.domain.model;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;

import java.util.HashSet;
import java.util.Optional;
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

    public boolean addProduct(Product product) throws CapacityExceededException {
        if (this.isFull()) {
            throw new CapacityExceededException("A quantidade de produtos não pode exceder a 20 unidades.");
        }

        if (this.hasProduct(product.getProductId())) {
            return false;
        }

        return this.products.add(product);
    }

    public void removeProduct(String productId) {
        Optional<Product> match = this.products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst();

        if (match.isEmpty()) return;
        this.products.remove(match.get());
    }

    public boolean isFull() {
        return this.products.size() > 19;
    }

    public boolean hasProduct(String productId) {
        return products.stream()
                .anyMatch(product -> product.getProductId().equals(productId));
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
}
