package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.model.Gateway;
import org.springframework.stereotype.Service;

@Service
public class RemoveProductUseCase {

    private final Gateway gateway;

    public RemoveProductUseCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public void execute(Input input) {
        gateway.removeProduct(input.userId(), input.productId());
    }

    public record Input(String userId, String productId) {
    }
}
