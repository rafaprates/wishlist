package com.wishlist.wishlist.infrastructure.controller;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import com.wishlist.wishlist.usecase.AddProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WishlistController {

    private final AddProductUseCase addProductUseCase;

    public WishlistController(AddProductUseCase addProductUseCase) {
        this.addProductUseCase = addProductUseCase;
    }

    @PostMapping("/users/{userId}/wishlist")
    public ResponseEntity<Void> addProduct(@PathVariable String userId,
                                           @RequestBody AddProductRequest request) throws CapacityExceededException {
        addProductUseCase.execute(new AddProductUseCase.Input(userId, request.productId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    public record AddProductRequest(String productId) {
    }
}
