package com.wishlist.wishlist.infrastructure.advice;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CapacityExceededException.class)
    public ResponseEntity<AdviceResponse> handleCapacityExceededException(CapacityExceededException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AdviceResponse(e.getMessage()));
    }

    private record AdviceResponse(String message) {
    }
}
