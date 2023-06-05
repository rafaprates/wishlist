package com.wishlist.wishlist.infrastructure.controller;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import com.wishlist.wishlist.usecase.AddProductUseCase;
import com.wishlist.wishlist.usecase.FindAllByUserUseCase;
import com.wishlist.wishlist.usecase.RemoveProductUseCase;
import com.wishlist.wishlist.usecase.SearchProductByUserUseCase;
import com.wishlist.wishlist.usecase.common.ProductOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class WishlistController {

    private final AddProductUseCase addProductUseCase;
    private final RemoveProductUseCase removeProductUseCase;
    private final SearchProductByUserUseCase searchProductByUserUseCase;
    private final FindAllByUserUseCase findAllByUserUseCase;

    public WishlistController(AddProductUseCase addProductUseCase,
                              RemoveProductUseCase removeProductUseCase,
                              SearchProductByUserUseCase searchProductByUserUseCase,
                              FindAllByUserUseCase findAllByUserUseCase
    ) {
        this.addProductUseCase = addProductUseCase;
        this.removeProductUseCase = removeProductUseCase;
        this.searchProductByUserUseCase = searchProductByUserUseCase;
        this.findAllByUserUseCase = findAllByUserUseCase;
    }

    @PostMapping("wishlist/{userId}/products")
    public ResponseEntity<Void> addProduct(@PathVariable String userId,
                                           @RequestBody AddProductRequest request) throws CapacityExceededException {
        addProductUseCase.execute(new AddProductUseCase.Input(userId, request.productId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/wishlist/{userId}/products")
    public ResponseEntity<Set<ProductResponse>> findUserProducts(@PathVariable String userId,
                                                                 @RequestParam(required = false) Optional<String> productId) {
        ProductOutput output;
        if (productId.isPresent()) {
            output = searchProductByUserUseCase.execute(new SearchProductByUserUseCase.Input(userId, productId.get()));
        } else {
            output = findAllByUserUseCase.execute(new FindAllByUserUseCase.Input(userId));
        }
        Set<ProductResponse> productsResponse = output.products().stream()
                .map(product -> new ProductResponse(product.getProductId()))
                .collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.OK).body(productsResponse);
    }

    @DeleteMapping("/wishlist/{userId}/products/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable String userId,
                                              @PathVariable String productId) {
        removeProductUseCase.execute(new RemoveProductUseCase.Input(userId, productId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public record AddProductRequest(String productId) {
    }

    public record ProductResponse(String productId) {
    }
}
