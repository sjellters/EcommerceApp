package com.xcale.ecommerce.use_cases.delete_cart;

import com.xcale.ecommerce.exceptions.EntityNotFoundException;
import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.CartGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCartUseCaseTest {

    @Mock
    private CartGateway cartGateway;

    @InjectMocks
    private DeleteCartUseCase deleteCartUseCase;

    private DeleteCartUseCaseParams params;

    @BeforeEach
    void setUp() {
        params = new DeleteCartUseCaseParams();
        params.setCartId(UUID.randomUUID().toString());
    }

    @Test
    void givenDeleteCartUseCaseParams_whenCartExists_thenDeleteCart() {
        Cart cart = mock(Cart.class);
        cart.setId(params.getCartId());

        when(cartGateway.findById(params.getCartId())).thenReturn(Optional.of(cart));

        DeleteCartUseCaseResult result = deleteCartUseCase.execute(params);

        verify(cartGateway, times(1)).delete(cart);

        assertEquals("Cart with id " + params.getCartId() + " was deleted successfully", result.getSuccessMessage());
    }

    @Test
    void givenDeleteCartUseCaseParams_whenCartDoesNotExist_thenThrowEntityNotFoundException() {
        when(cartGateway.findById(params.getCartId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> deleteCartUseCase.execute(params));

        verify(cartGateway, never()).delete(any(Cart.class));
    }
}