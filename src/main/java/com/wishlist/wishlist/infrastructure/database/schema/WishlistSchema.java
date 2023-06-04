package com.wishlist.wishlist.infrastructure.database.schema;

import com.wishlist.wishlist.domain.model.Wishlist;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("wishlist")
public class WishlistSchema {

    @Id
    private String userId;

    private Set<String> productIds;

    public WishlistSchema(String userId, Set<String> productIds) {
        this.userId = userId;
        this.productIds = productIds;
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

    public void setProductIds(Set<String> productIds) {
        this.productIds = productIds;
    }

    public Wishlist toDomain() {
        return new Wishlist(this.userId, this.productIds);
    }
}
