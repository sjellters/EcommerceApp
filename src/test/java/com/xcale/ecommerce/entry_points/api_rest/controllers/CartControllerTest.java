package com.xcale.ecommerce.entry_points.api_rest.controllers;

import com.xcale.ecommerce.entry_points.api_rest.dtos.CartDTO;
import com.xcale.ecommerce.entry_points.api_rest.mappers.UseCaseParamsMapper;
import com.xcale.ecommerce.entry_points.api_rest.mappers.UseCaseResultMapper;
import com.xcale.ecommerce.exceptions.EntityNotFoundException;
import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.use_cases.get_cart.GetCartUseCase;
import com.xcale.ecommerce.use_cases.get_cart.GetCartUseCaseParams;
import com.xcale.ecommerce.use_cases.get_cart.GetCartUseCaseResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UseCaseParamsMapper useCaseParamsMapper;

    @Mock
    private UseCaseResultMapper useCaseResultMapper;

    @Mock
    private GetCartUseCase getCartUseCase;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    void getCart_shouldReturnCartDTO_whenCartExists() throws Exception {
        // given
        String cartId = UUID.randomUUID().toString();
        GetCartUseCaseParams params = new GetCartUseCaseParams(cartId);
        GetCartUseCaseResult result = new GetCartUseCaseResult(new Cart());
        when(useCaseParamsMapper.toGetCartUseCaseParams(cartId)).thenReturn(params);
        when(getCartUseCase.execute(params)).thenReturn(result);
        when(useCaseResultMapper.toGetCartResponse(result)).thenReturn(new CartDTO());

        // when + then
        mockMvc.perform(get("/cart/{id}", cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getCart_shouldReturnNotFound_whenCartDoesNotExist() throws Exception {
        // given
        String cartId = UUID.randomUUID().toString();
        GetCartUseCaseParams params = new GetCartUseCaseParams(cartId);
        when(useCaseParamsMapper.toGetCartUseCaseParams(cartId)).thenReturn(params);
        when(getCartUseCase.execute(params)).thenThrow(EntityNotFoundException.class);

        // when + then
        mockMvc.perform(get("/cart/{id}", cartId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCart_shouldReturnConflict_whenUseCaseFails() throws Exception {
        // Arrange
        String id = "1234";
        GetCartUseCaseParams params = new GetCartUseCaseParams(id);

        when(useCaseParamsMapper.toGetCartUseCaseParams(anyString()))
                .thenReturn(params);

        when(getCartUseCase.execute(any(GetCartUseCaseParams.class)))
                .thenThrow(new Exception());

        // Act
        mockMvc.perform(get("/cart/{id}", id))
                .andExpect(status().isConflict())
                .andReturn();
    }
}
