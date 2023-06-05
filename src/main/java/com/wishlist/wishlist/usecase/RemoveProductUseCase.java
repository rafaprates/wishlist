package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Wishlist;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RemoveProductUseCase {

    private final Gateway gateway;

    public RemoveProductUseCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public void execute(Input input) {
        Optional<Wishlist> potential = gateway.findUserWishlist(input.userId());

        if (potential.isEmpty()) return;

        Wishlist wishlist = potential.get();
        wishlist.removeProduct(input.productId());

        gateway.save(wishlist);
    }

    public record Input(String userId, String productId) {
    }
}
