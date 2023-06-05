package com.wishlist.wishlist.infrastructure.gateway;

import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Wishlist;
import com.wishlist.wishlist.infrastructure.database.repository.WishlistMongoRepository;
import com.wishlist.wishlist.infrastructure.database.schema.ProductSchema;
import com.wishlist.wishlist.infrastructure.database.schema.WishlistSchema;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GatewayImpl implements Gateway {

    private final WishlistMongoRepository wishlistMongoRepository;
    private final MongoTemplate mongoTemplate;

    public GatewayImpl(WishlistMongoRepository wishlistMongoRepository, MongoTemplate mongoTemplate) {
        this.wishlistMongoRepository = wishlistMongoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Wishlist save(Wishlist wishlist) {
        Set<ProductSchema> productSchemas = wishlist.getProducts().stream()
                .map(product -> new ProductSchema(product.getProductId()))
                .collect(Collectors.toSet());

        return wishlistMongoRepository.save(new WishlistSchema(wishlist.getUserId(), productSchemas))
                .toDomain();
    }

    @Override
    public Optional<Wishlist> findUserWishlist(String userId) {
        return wishlistMongoRepository.findById(userId)
                .map(WishlistSchema::toDomain);
    }
}
