package com.xcale.ecommerce.use_cases.get_cart;

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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCartUseCaseTest {

    @Mock
    private CartGateway cartGateway;

    @InjectMocks
    private GetCartUseCase getCartUseCase;

    private GetCartUseCaseParams params;

    @BeforeEach
    void setUp() {
        params = new GetCartUseCaseParams();
        params.setCartId(UUID.randomUUID().toString());
    }

    @Test
    public void testGetCartSuccess() throws Exception {
        Cart expectedCart = Cart.builder().id(params.getCartId()).build();
        when(cartGateway.findById(params.getCartId())).thenReturn(Optional.of(expectedCart));

        GetCartUseCaseResult result = getCartUseCase.execute(params);

        assertEquals(expectedCart.getId(), result.getCart().getId());
    }

    @Test
    public void testGetCartNotFound() {
        when(cartGateway.findById(params.getCartId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> getCartUseCase.execute(params));
    }
}