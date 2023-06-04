package com.wishlist.wishlist.infrastructure.database.schema;

import com.wishlist.wishlist.domain.model.Product;

public class ProductSchema {

    private String productId;

    public ProductSchema(String productId) {
        this.productId = productId;
    }

    public Product toDomain() {
        return new Product(this.productId);
    }
}
