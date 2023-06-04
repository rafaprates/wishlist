package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.exception.UserNotFoundException;
import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Product;
import com.wishlist.wishlist.domain.model.Wishlist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Search Product Use Case")
class SearchProductByUserUseCaseTest {

    @Mock
    private Gateway gateway;

    @InjectMocks
    private SearchProductByUserUseCase searchProductByUserUseCase;

    @Test
    @DisplayName("Should throw UserNotFoundException when provided user has no wishlist")
    void shouldThrowException_whenUserIdIsNotValid() throws UserNotFoundException {
        // given
        when(gateway.findUserWishlist(anyString())).thenReturn(Optional.empty());
        SearchProductByUserUseCase.Input input = new SearchProductByUserUseCase.Input("userId", "productId");

        // when, expect
        assertThrows(UserNotFoundException.class, () -> searchProductByUserUseCase.execute(input));
    }

    @Test
    @DisplayName("Should return an empty list when product is not found")
    void shouldReturnEmptyList_whenProductIsNotFound() throws UserNotFoundException {
        // given
        Wishlist existingWishlist = new Wishlist("userId", Set.of(new Product("product1")));
        when(gateway.findUserWishlist(anyString())).thenReturn(Optional.of(existingWishlist));
        SearchProductByUserUseCase.Input input = new SearchProductByUserUseCase.Input("userId", "product2");

        // when
        SearchProductByUserUseCase.Output output = searchProductByUserUseCase.execute(input);

        // expect
        assertTrue(output.products().isEmpty());
    }

    @Test
    @DisplayName("Should find product when product is present")
    void shouldFindProduct_whenProductIsPresent() throws UserNotFoundException {
        // given
        Wishlist existingWishlist = new Wishlist("userId", Set.of(new Product("product1")));
        when(gateway.findUserWishlist(anyString())).thenReturn(Optional.of(existingWishlist));
        SearchProductByUserUseCase.Input input = new SearchProductByUserUseCase.Input("userId", "product1");

        // when
        SearchProductByUserUseCase.Output output = searchProductByUserUseCase.execute(input);

        // expect
        assertEquals("product1", output.products().stream().findFirst().get().getProductId());
    }

    @Test
    @DisplayName("Should return a list of size one when product is present")
    void shouldReturnSingleItemOnList_whenProductIsPresent() throws UserNotFoundException {
        // given
        Wishlist existingWishlist = new Wishlist("userId", Set.of(new Product("product1")));
        when(gateway.findUserWishlist(anyString())).thenReturn(Optional.of(existingWishlist));
        SearchProductByUserUseCase.Input input = new SearchProductByUserUseCase.Input("userId", "product1");

        // when
        SearchProductByUserUseCase.Output output = searchProductByUserUseCase.execute(input);

        // expect
        assertEquals(1, output.products().size());
    }

}