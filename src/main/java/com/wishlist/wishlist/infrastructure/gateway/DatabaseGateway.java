package com.wishlist.wishlist.infrastructure.gateway;

import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Wishlist;
import com.wishlist.wishlist.infrastructure.database.repository.WishlistMongoRepository;
import com.wishlist.wishlist.infrastructure.database.schema.WishlistSchema;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DatabaseGateway implements Gateway {

    private final WishlistMongoRepository wishlistMongoRepository;

    public DatabaseGateway(WishlistMongoRepository wishlistMongoRepository) {
        this.wishlistMongoRepository = wishlistMongoRepository;
    }

    @Override
    public Wishlist save(Wishlist wishlist) {
        return wishlistMongoRepository.save(new WishlistSchema(wishlist.getUserId(), wishlist.getProductIds()))
                .toDomain();
    }

    @Override
    public Optional<Wishlist> findUserWishlist(String userId) {
        return wishlistMongoRepository.findByUserId(userId)
                .map(WishlistSchema::toDomain);
    }
}
