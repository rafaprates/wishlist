package com.wishlist.wishlist.domain.exception;

public class CapacityExceededException extends Exception {

    public CapacityExceededException(String message) {
        super(message);
    }
}
