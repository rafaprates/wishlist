package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Wishlist;
import org.springframework.stereotype.Service;

@Service
public class AddProductUseCase {

    private final Gateway gateway;

    public AddProductUseCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public Wishlist execute(Input input) throws CapacityExceededException {
        Wishlist wishlist = gateway.findUserWishlist(input.userId)
                .orElseGet(() -> createNewWishlist(input.userId));

        if (wishlist.isFull()) {
            throw new CapacityExceededException("A quantidade de produtos não pode exceder a 20 unidades.");
        }

        if (wishlist.containsProduct(input.productId)) {
            return wishlist;
        }

        wishlist.addProduct(input.productId);
        return gateway.save(wishlist);
    }

    private Wishlist createNewWishlist(String userId) {
        return new Wishlist(userId);
    }

    public record Input(String userId, String productId) {
    }
}
