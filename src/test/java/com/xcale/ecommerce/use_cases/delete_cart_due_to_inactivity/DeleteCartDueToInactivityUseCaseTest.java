package com.xcale.ecommerce.use_cases.delete_cart_due_to_inactivity;

import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.CartGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCartDueToInactivityUseCaseTest {

    @InjectMocks
    private DeleteCartDueToInactivityUseCase useCase;

    @Mock
    private CartGateway cartGateway;

    @Test
    void testDeleteInactiveCarts() {
        // Given
        List<Cart> cartList = Arrays.asList(
                Cart.builder().modificationDate(Instant.now().minus(11, ChronoUnit.MINUTES)).build(),
                Cart.builder().modificationDate(Instant.now().minus(5, ChronoUnit.MINUTES)).build(),
                Cart.builder().modificationDate(Instant.now().minus(15, ChronoUnit.MINUTES)).build()
        );

        when(cartGateway.findAll()).thenReturn(cartList);

        // When
        useCase.execute(new DeleteCartDueToInactivityUseCaseParams());

        // Then
        verify(cartGateway, times(1)).delete(cartList.get(0));
        verify(cartGateway, never()).delete(cartList.get(1));
        verify(cartGateway, times(1)).delete(cartList.get(2));
    }

    @Test
    void testDoNotDeleteActiveCarts() {
        // Given
        List<Cart> cartList = Arrays.asList(
                Cart.builder().modificationDate(Instant.now().minus(2, ChronoUnit.MINUTES)).build(),
                Cart.builder().modificationDate(Instant.now().minus(5, ChronoUnit.MINUTES)).build(),
                Cart.builder().modificationDate(Instant.now().minus(7, ChronoUnit.MINUTES)).build()
        );
        when(cartGateway.findAll()).thenReturn(cartList);

        // When
        useCase.execute(new DeleteCartDueToInactivityUseCaseParams());

        // Then
        verify(cartGateway, never()).delete(any());
    }
}