package com.wishlist.wishlist.usecase.common;

import com.wishlist.wishlist.domain.model.Product;

import java.util.Set;

public record ProductOutput(Set<Product> products) {
}
