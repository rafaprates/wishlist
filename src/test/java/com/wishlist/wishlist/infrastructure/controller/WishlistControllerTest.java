package com.wishlist.wishlist.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wishlist.wishlist.domain.exception.CapacityExceededException;
import com.wishlist.wishlist.domain.model.Product;
import com.wishlist.wishlist.usecase.AddProductUseCase;
import com.wishlist.wishlist.usecase.FindAllByUserUseCase;
import com.wishlist.wishlist.usecase.RemoveProductUseCase;
import com.wishlist.wishlist.usecase.SearchProductByUserUseCase;
import com.wishlist.wishlist.usecase.common.ProductOutput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WishlistController.class)
@DisplayName("Wishlist Controller")
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AddProductUseCase addProductUseCase;

    @MockBean
    private FindAllByUserUseCase findAllByUserUseCase;

    @MockBean
    private RemoveProductUseCase removeProductUseCase;

    @MockBean
    private SearchProductByUserUseCase searchProductByUserUseCase;

    @Test
    @DisplayName("Should return 201 when product is added")
    void shouldReturn201_whenProductIsAdded() throws Exception {
        WishlistController.AddProductRequest request = new WishlistController.AddProductRequest("productId");
        mockMvc.perform(post("/wishlist/{userId}/products", "userId")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should return 400 when wishlist is full")
    void shouldReturn400_whenWishlistIsFull() throws Exception {
        // given
        WishlistController.AddProductRequest request = new WishlistController.AddProductRequest("productId");
        doThrow(new CapacityExceededException("A quantidade de produtos não deve exceder a 20 unidades"))
                .when(addProductUseCase)
                .execute(any());

        // act & assert
        mockMvc.perform(post("/wishlist/{userId}/products", "userId")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 200 when returning all products")
    void shouldReturn200_whenReturnAllProducts() throws Exception {
        // given
        when(findAllByUserUseCase.execute(any())).thenReturn(
                new ProductOutput(Set.of(new Product("productId")))
        );

        // act & assert
        mockMvc.perform(get("/wishlist/{userId}/products", "userId")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 200 when returning one product")
    void shouldReturn200_whenReturnOneProduct() throws Exception {
        // given
        when(searchProductByUserUseCase.execute(any())).thenReturn(
                new ProductOutput(Set.of(new Product("productId")))
        );

        // act & assert
        mockMvc.perform(get("/wishlist/{userId}/products", "userId")
                        .contentType("application/json")
                        .param("productId", "productId"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 204 when product is removed")
    void shouldReturn204_whenProductIsRemoved() throws Exception {
        // act & assert
        mockMvc.perform(delete("/wishlist/{userId}/products/{productId}", "userId", "productId")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }
}