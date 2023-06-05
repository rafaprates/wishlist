package com.wishlist.wishlist.domain.model;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Domain model: Wishlist")
class WishlistTest {

    @Test
    @DisplayName("Should throw exception when Wishlist is full")
    void shouldThrowException_whenWishlistIsFull() throws CapacityExceededException {
        // given
        Wishlist wishlist = new Wishlist("userId", generateProducts(20));

        // when, expect
        assertThrows(CapacityExceededException.class, () -> wishlist.addProduct(new Product("newProductId")));
    }

    @Test
    @DisplayName("Should add product when Wishlist is not full")
    void shouldAdd_whenWishlistIsNotFull() throws CapacityExceededException {
        // given
        Wishlist wishlist = new Wishlist("userId", generateProducts(10));

        // when
        wishlist.addProduct(new Product("newProductId"));

        // expect
        assertTrue(wishlist.hasProduct("newProductId"));
    }

    @Test
    @DisplayName("Should not add product when product is duplicate")
    void shouldNotAdd_whenProductIsDuplicate() throws CapacityExceededException {
        // given
        Wishlist wishlist = new Wishlist("userId", generateProducts(10));

        // when
        wishlist.addProduct(new Product("newProductId"));
        boolean success = wishlist.addProduct(new Product("newProductId"));

        // expect
        assertFalse(success);
    }

    @Test
    @DisplayName("Should remove product when it exists")
    public void shouldRemoveProduct_whenItExists() throws CapacityExceededException {
        // given
        Wishlist wishlist = new Wishlist("randomUserId");
        Product product = new Product("randomProductId");
        wishlist.addProduct(product);

        // when
        wishlist.removeProduct(product.getProductId());

        // assert
        assertFalse(wishlist.hasProduct(product.getProductId()));
    }

    @Test
    @DisplayName("Should be full when has over 20 products, but not when it has less than 20 products")
    void shouldBeFull_whenOver20Products_butNotWhenLessThan20() throws CapacityExceededException {
        // given
        Wishlist wishlist = new Wishlist("randomUserId");

        for (int i = 0; i < 20; i++) {
            Product product = new Product(Integer.toString(i));
            wishlist.addProduct(product);
        }

        // assert
        assertTrue(wishlist.isFull());

        // given
        wishlist.removeProduct("0");

        // assert
        assertFalse(wishlist.isFull());
    }

    private Set<Product> generateProducts(int count) {
        Set<Product> products = new HashSet<>();
        for (int i = 1; i <= count; i++) {
            products.add(new Product("product" + i));
        }
        return products;
    }
}