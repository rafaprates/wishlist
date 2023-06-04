package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Product;
import com.wishlist.wishlist.domain.model.Wishlist;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FindAllByUserUseCase {

    private final Gateway gateway;

    public FindAllByUserUseCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public Output execute(Input input) {
        Wishlist wishlist = gateway.findUserWishlist(input.userId())
                .orElseGet(() -> createEmptyWishlist(input.userId()));
        return new Output(wishlist.getUserId(), wishlist.getProducts());
    }

    private Wishlist createEmptyWishlist(String userId) {
        return new Wishlist(userId);
    }

    public record Input(String userId) {
    }

    public record Output(String userId, Set<Product> products) {
    }
}
