package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Wishlist;
import com.wishlist.wishlist.usecase.common.ProductOutput;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class FindAllByUserUseCase {

    private final Gateway gateway;

    public FindAllByUserUseCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public ProductOutput execute(Input input) {
        Optional<Wishlist> wishlist = gateway.findUserWishlist(input.userId());

        if (wishlist.isPresent()) {
            return new ProductOutput(wishlist.get().getProducts());
        }
        return new ProductOutput(Set.of());
    }

    public record Input(String userId) {
    }
}
