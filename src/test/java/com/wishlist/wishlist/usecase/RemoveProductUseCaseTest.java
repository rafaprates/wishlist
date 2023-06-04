package com.wishlist.wishlist.usecase;

import com.wishlist.wishlist.domain.model.Gateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Remove Product Use Case")
class RemoveProductUseCaseTest {

    @Mock
    private Gateway gateway;

    @InjectMocks
    private RemoveProductUseCase removeProductUseCase;

    @Test
    @DisplayName("Should remove product")
    void shouldRemove_whenProductExists() {
        // given
        RemoveProductUseCase.Input input = new RemoveProductUseCase.Input("userId", "productId");

        // when
        removeProductUseCase.execute(input);

        // expect
        verify(gateway, times(1)).removeUserProduct("userId", "productId");
    }

}