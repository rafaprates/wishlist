package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.model.Gateway;
import com.wishlist.wishlist.domain.model.Wishlist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Use case: Remove Product")
class RemoveProductUseCaseTest {

    @Mock
    private Gateway gateway;

    @InjectMocks
    private RemoveProductUseCase removeProductUseCase;

    @Test
    @DisplayName("Should remove product")
    void shouldRemove_whenProductExists() {
        // given
        when(gateway.findUserWishlist(any())).thenReturn(Optional.of(new Wishlist("userId")));
        RemoveProductUseCase.Input input = new RemoveProductUseCase.Input("userId", "productId");

        // when
        removeProductUseCase.execute(input);

        // expect
        verify(gateway, times(1)).findUserWishlist(input.userId());
        verify(gateway, times(1)).save(any(Wishlist.class));
    }

}