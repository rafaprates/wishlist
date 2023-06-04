package com.wishlist.wishlist.infrastructure.controller;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import com.wishlist.wishlist.usecase.AddProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wishlist")
@RestController
public class WishlistController {

    private final AddProductUseCase addProductUseCase;

    public WishlistController(AddProductUseCase addProductUseCase) {
        this.addProductUseCase = addProductUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody AddProductRequest request) throws CapacityExceededException {
        addProductUseCase.execute(new AddProductUseCase.Input(request.userId, request.productId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public record AddProductRequest(String userId, String productId) {
    }
}
