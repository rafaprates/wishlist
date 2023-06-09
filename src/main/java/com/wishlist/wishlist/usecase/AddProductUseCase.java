package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Product;
import com.wishlist.wishlist.domain.model.Wishlist;
import org.springframework.stereotype.Service;

@Service
public class AddProductUseCase {

    private final Gateway gateway;

    public AddProductUseCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public Wishlist execute(Input input) throws CapacityExceededException {
        Wishlist wishlist = gateway.findUserWishlist(input.userId())
                .orElseGet(() -> createNewWishlist(input.userId()));

        wishlist.addProduct(new Product(input.productId()));

        return gateway.save(wishlist);
    }

    private Wishlist createNewWishlist(String userId) {
        return new Wishlist(userId);
    }

    public record Input(String userId, String productId) {
    }
}
