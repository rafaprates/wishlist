package com.wishlist.wishlist.infrastructure.database.repository;

import com.wishlist.wishlist.infrastructure.database.schema.WishlistSchema;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WishlistMongoRepository extends MongoRepository<WishlistSchema, String> {

    Optional<WishlistSchema> findByUserId(String userId);

    void deleteByUserIdAndProductIds(String userId, String productId);
}
