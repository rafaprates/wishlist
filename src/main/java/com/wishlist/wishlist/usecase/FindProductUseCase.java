package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.model.Gateway;
import org.springframework.stereotype.Service;

@Service
public class FindProductUseCase {

    private final Gateway gateway;

    public FindProductUseCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public Output execute(Input input) {
        return null;
    }

    public record Input(String userId, String productId) {
    }

    public record Output(String userId, String productId) {
    }
}
