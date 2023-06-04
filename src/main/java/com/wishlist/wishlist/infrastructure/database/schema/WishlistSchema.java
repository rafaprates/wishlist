package com.wishlist.wishlist.infrastructure.database.schema;

import com.wishlist.wishlist.domain.model.Product;
import com.wishlist.wishlist.domain.model.Wishlist;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.stream.Collectors;

@Document("wishlist")
public class WishlistSchema {

    @Id
    private String userId;

    private Set<ProductSchema> products;

    public WishlistSchema(String userId, Set<ProductSchema> products) {
        this.userId = userId;
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<ProductSchema> getProducts() {
        return products;
    }

    public void setProductIds(Set<ProductSchema> products) {
        this.products = products;
    }

    public Wishlist toDomain() {
        return new Wishlist(this.userId,
                this.products.stream()
                        .map(ProductSchema::toDomain)
                        .collect(Collectors.toSet()));
    }
}
