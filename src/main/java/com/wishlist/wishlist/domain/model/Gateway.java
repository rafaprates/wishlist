package com.wishlist.wishlist.domain.model;

import java.util.Optional;

public interface Gateway {

    Wishlist save(Wishlist wishlist);

    Optional<Wishlist> findUserWishlist(String userId);

    void removeProduct(String userId, String productId);
}
