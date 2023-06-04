package com.wishlist.wishlist.infrastructure.gateway;

import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Wishlist;
import com.wishlist.wishlist.infrastructure.database.repository.WishlistMongoRepository;
import com.wishlist.wishlist.infrastructure.database.schema.ProductSchema;
import com.wishlist.wishlist.infrastructure.database.schema.WishlistSchema;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GatewayImpl implements Gateway {

    private final WishlistMongoRepository wishlistMongoRepository;

    public GatewayImpl(WishlistMongoRepository wishlistMongoRepository) {
        this.wishlistMongoRepository = wishlistMongoRepository;
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
        Optional<WishlistSchema> byId = wishlistMongoRepository.findById(userId);
        return byId
                .map(WishlistSchema::toDomain);
    }

    @Override
    public Wishlist findByUserId(String userId) {
        return null;
    }

    @Override
    public void removeUserProduct(String userId, String productId) {
        wishlistMongoRepository.deleteByUserIdAndProductIds(userId, productId);
    }

    @Override
    public Wishlist findByUserIdAndProductId(String userId, String productId) {
        return null;
    }
}
