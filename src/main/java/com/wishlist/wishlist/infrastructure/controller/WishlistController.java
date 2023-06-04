package com.wishlist.wishlist.infrastructure.controller;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import com.wishlist.wishlist.domain.exception.UserNotFoundException;
import com.wishlist.wishlist.usecase.AddProductUseCase;
import com.wishlist.wishlist.usecase.FindAllByUserUseCase;
import com.wishlist.wishlist.usecase.RemoveProductUseCase;
import com.wishlist.wishlist.usecase.SearchProductByUserUseCase;
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

    @PostMapping("/users/{userId}/wishlist")
    public ResponseEntity<Void> addProduct(@PathVariable String userId,
                                           @RequestBody AddProductRequest request) throws CapacityExceededException {
        addProductUseCase.execute(new AddProductUseCase.Input(userId, request.productId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users/{userId}/wishlist")
    public ResponseEntity<WishlistResponse> findByUser(@PathVariable String userId,
                                                       @RequestParam(required = false) Optional<String> productId) throws UserNotFoundException {

        if (productId.isPresent()) {
            SearchProductByUserUseCase.Output output = searchProductByUserUseCase.execute(new SearchProductByUserUseCase.Input(userId, productId.get()));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new WishlistResponse(output.userId(), output.products().stream()
                            .map(product -> new ProductResponse(product.getProductId()))
                            .collect(Collectors.toSet())
                    )
            );
        } else {
            FindAllByUserUseCase.Output output = findAllByUserUseCase.execute(new FindAllByUserUseCase.Input(userId));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new WishlistResponse(output.userId(), output.products().stream()
                            .map(product -> new ProductResponse(product.getProductId()))
                            .collect(Collectors.toSet())
                    )
            );
        }
    }

    @DeleteMapping("users/{userId}/wishlist/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable String userId,
                                              @PathVariable String productId) {
        removeProductUseCase.execute(new RemoveProductUseCase.Input(userId, productId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public record AddProductRequest(String productId) {
    }

    public record WishlistResponse(String userId, Set<ProductResponse> products) {
    }

    public record ProductResponse(String productId) {
    }
}
