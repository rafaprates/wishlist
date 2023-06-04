package com.wishlist.wishlist.infrastructure.controller;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import com.wishlist.wishlist.usecase.AddProductUseCase;
import com.wishlist.wishlist.usecase.RemoveProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WishlistController {

    private final AddProductUseCase addProductUseCase;
    private final RemoveProductUseCase removeProductUseCase;

    public WishlistController(AddProductUseCase addProductUseCase, RemoveProductUseCase removeProductUseCase) {
        this.addProductUseCase = addProductUseCase;
        this.removeProductUseCase = removeProductUseCase;
    }

    @PostMapping("/users/{userId}/wishlist")
    public ResponseEntity<Void> addProduct(@PathVariable String userId,
                                           @RequestBody AddProductRequest request) throws CapacityExceededException {
        addProductUseCase.execute(new AddProductUseCase.Input(userId, request.productId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("users/{userId}/wishlist/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable String userId,
                                              @PathVariable String productId) {
        removeProductUseCase.execute(new RemoveProductUseCase.Input(userId, productId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    public record AddProductRequest(String productId) {
    }
}
