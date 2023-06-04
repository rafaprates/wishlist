package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Product;
import com.wishlist.wishlist.domain.model.Wishlist;
import com.wishlist.wishlist.usecase.AddProductUseCase.Input;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Add Product Use Case")
class AddProductUseCaseTest {

    @Mock
    private Gateway gateway;

    @InjectMocks
    private AddProductUseCase addProductUseCase;

    @Test
    @DisplayName("Should throw exception when Wishlist is full")
    void shouldThrowException_whenWishlistIsFull() throws CapacityExceededException {
        // given
        Wishlist existingWishlist = new Wishlist("userId", generateProducts(20));
        when(gateway.findUserWishlist(anyString())).thenReturn(Optional.of(existingWishlist));
        Input input = new Input("userId", "product21");

        // when, expect
        assertThrows(CapacityExceededException.class, () -> addProductUseCase.execute(input));
    }

    @Test
    @DisplayName("Should add product when Wishlist is not full")
    void shouldAdd_whenWishlistIsNotFull() throws CapacityExceededException {
        // given
        Wishlist existingWishlist = new Wishlist("userId", generateProducts(10));
        when(gateway.findUserWishlist(anyString())).thenReturn(Optional.of(existingWishlist));
        Input input = new Input("userId", "product11");

        // when
        addProductUseCase.execute(input);

        // expect
        verify(gateway, times(1)).save(any(Wishlist.class));
    }

    @Test
    @DisplayName("Should not add product when product is duplicate")
    void shouldNotAdd_whenProductIsDuplicate() throws CapacityExceededException {
        // given
        Wishlist existingWishlist = new Wishlist("userId", generateProducts(10));
        existingWishlist.addProduct(new Product("duplicateProductId"));
        when(gateway.findUserWishlist(anyString())).thenReturn(Optional.of(existingWishlist));
        Input input = new Input("userId", "duplicateProductId");

        // when
        addProductUseCase.execute(input);

        // expect
        verify(gateway, times(0)).save(any(Wishlist.class));
    }

    @Test
    @DisplayName("Should create new Wishlist when user adds a product for the first time")
    void shouldCreateNewWishlist_whenWishlistDoesNotExistForUser() throws CapacityExceededException {
        // given
        when(gateway.findUserWishlist(anyString())).thenReturn(Optional.empty());
        Input input = new Input("userId", "productId");

        // when
        addProductUseCase.execute(input);

        // expect
        verify(gateway, times(1)).save(any(Wishlist.class));
    }

    private Set<Product> generateProducts(int count) {
        Set<Product> products = new HashSet<>();
        for (int i = 1; i <= count; i++) {
            products.add(new Product("product" + i));
        }
        return products;
    }
}