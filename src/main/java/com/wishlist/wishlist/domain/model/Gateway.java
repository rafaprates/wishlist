package com.wishlist.wishlist.domain.model;

import java.util.Optional;

public interface Gateway {

    Wishlist save(Wishlist wishlist);

    void removeUserProduct(String userId, String productId);

    Optional<Wishlist> findUserWishlist(String userId);

    Wishlist findByUserId(String userId);
}
