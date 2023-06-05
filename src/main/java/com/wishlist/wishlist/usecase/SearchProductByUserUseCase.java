package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.exception.UserNotFoundException;
import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Product;
import com.wishlist.wishlist.domain.model.Wishlist;
import com.wishlist.wishlist.usecase.common.ProductOutput;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchProductByUserUseCase {

    private final Gateway gateway;

    public SearchProductByUserUseCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public ProductOutput execute(Input input) throws UserNotFoundException {
        Wishlist wishlist = gateway.findUserWishlist(input.userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        Set<Product> matchingProducts = wishlist.getProducts().stream()
                .filter(product -> product.getProductId().equals(input.productId))
                .collect(Collectors.toSet());

        return new ProductOutput(matchingProducts);
    }

    public record Input(String userId, String productId) {
    }

    public record Output(Set<Product> products) {
    }
}
