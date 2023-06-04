package com.wishlist.wishlist.infrastructure.database.repository;

import com.wishlist.wishlist.infrastructure.database.schema.WishlistSchema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface WishlistMongoRepository extends MongoRepository<WishlistSchema, String> {

    void deleteByUserIdAndProductIds(String userId, String productId);
}
