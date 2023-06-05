package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Product;
import com.wishlist.wishlist.domain.model.Wishlist;
import com.wishlist.wishlist.usecase.common.ProductOutput;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchProductByUserUseCase {

    private final Gateway gateway;

    public SearchProductByUserUseCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public ProductOutput execute(Input input) {
        Optional<Wishlist> wishlist = gateway.findUserWishlist(input.userId);

        if (wishlist.isPresent()) {
            Set<Product> matchingProducts = wishlist.get().getProducts().stream()
                    .filter(product -> product.getProductId().equals(input.productId))
                    .collect(Collectors.toSet());
            return new ProductOutput(matchingProducts);
        }
        return new ProductOutput(Set.of());
    }

    public record Input(String userId, String productId) {
    }
}
