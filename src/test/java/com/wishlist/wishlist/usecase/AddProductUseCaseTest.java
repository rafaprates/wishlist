package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Wishlist;
import com.wishlist.wishlist.usecase.AddProductUseCase.Input;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Use case: Add Product")
class AddProductUseCaseTest {

    @Mock
    private Gateway gateway;

    @InjectMocks
    private AddProductUseCase addProductUseCase;


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
}