package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Product;
import com.wishlist.wishlist.domain.model.Wishlist;
import com.wishlist.wishlist.usecase.common.ProductOutput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Find All Products Use Case")
class FindAllByUserUseCaseTest {

    @Mock
    private Gateway gateway;

    @InjectMocks
    private FindAllByUserUseCase findAllByUserUseCase;

    @Test
    @DisplayName("Should return an empty list of products when user has no wishlist (never added a product)")
    void shouldReturnEmptyList_whenUserHasNoWishlist() {
        // given
        when(gateway.findUserWishlist(anyString())).thenReturn(Optional.empty());
        FindAllByUserUseCase.Input input = new FindAllByUserUseCase.Input("userId");

        // when
        ProductOutput output = findAllByUserUseCase.execute(input);

        // expect
        assertTrue(output.products().isEmpty());
    }

    @Test
    @DisplayName("Should return products when user has wishlist")
    void shouldReturnWishlistWithProducts_whenUserAlreadyInsertedProducts() {
        // given
        Wishlist existingWishlist = new Wishlist("userId", Set.of(new Product("product1")));
        when(gateway.findUserWishlist(anyString())).thenReturn(Optional.of(existingWishlist));
        FindAllByUserUseCase.Input input = new FindAllByUserUseCase.Input("userId");

        // when
        ProductOutput output = findAllByUserUseCase.execute(input);

        // expect
        assertEquals(1, output.products().size());
    }
}