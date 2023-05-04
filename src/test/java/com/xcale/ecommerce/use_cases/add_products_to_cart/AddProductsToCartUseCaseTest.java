package com.xcale.ecommerce.use_cases.add_products_to_cart;

import com.xcale.ecommerce.exceptions.EntityNotFoundException;
import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.CartGateway;
import com.xcale.ecommerce.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddProductsToCartUseCaseTest {

    @Mock
    private CartGateway cartGateway;

    @InjectMocks
    private AddProductsToCartUseCase addProductsToCartUseCase;

    private AddProductsToCartUseCaseParams params;

    @BeforeEach
    void setUp() {
        params = new AddProductsToCartUseCaseParams();
        params.setCartId(UUID.randomUUID().toString());

        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id(1).description("Product 1").amount(10.0).build());
        products.add(Product.builder().id(2).description("Product 2").amount(20.0).build());
        params.setProducts(products);
    }

    @Test
    void whenCartExists_thenAddProductsToCart() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id(0).description("Product 0").amount(5.0).build());

        Cart cart = Cart.builder()
                .id(params.getCartId())
                .products(products)
                .modificationDate(Instant.now())
                .build();

        when(cartGateway.findById(params.getCartId())).thenReturn(Optional.of(cart));
        when(cartGateway.save(cart)).thenReturn(Cart.builder()
                .id(params.getCartId())
                .products(Arrays.asList(
                        Product.builder().id(0).description("Product 0").amount(5.0).build(),
                        Product.builder().id(1).description("Product 1").amount(10.0).build(),
                        Product.builder().id(2).description("Product 2").amount(20.0).build()
                ))
                .modificationDate(Instant.now().plus(10, java.time.temporal.ChronoUnit.SECONDS))
                .build()
        );

        AddProductsToCartUseCaseResult result = addProductsToCartUseCase.execute(params);

        assertEquals(result.getCart().getProducts().size(), 3);
        assertEquals(result.getCart().getProducts().get(0).getId(), 0);
        assertEquals(params.getProducts().get(0), result.getCart().getProducts().get(1));
        assertEquals(params.getProducts().get(1), result.getCart().getProducts().get(2));

        assertTrue(result.getCart().getModificationDate().isAfter(cart.getModificationDate()));
    }

    @Test
    void whenCartIdIsNull_thenCreateCartAndAddProducts() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id(0).description("Product 0").amount(5.0).build());

        when(cartGateway.save(any(Cart.class))).thenReturn(Cart.builder()
                .id(UUID.randomUUID().toString())
                .products(Collections.singletonList(
                        Product.builder().id(0).description("Product 0").amount(5.0).build()
                ))
                .modificationDate(Instant.now().plus(10, java.time.temporal.ChronoUnit.SECONDS))
                .build()
        );

        params.setCartId(null);

        AddProductsToCartUseCaseResult result = addProductsToCartUseCase.execute(params);

        assertNotNull(result);
        assertNotNull(result.getCart());
        assertNotNull(result.getCart().getId());
        assertEquals(products, result.getCart().getProducts());
        verify(cartGateway, times(1)).save(any(Cart.class));
        verifyNoMoreInteractions(cartGateway);
    }

    @Test
    void whenCartIdIsNotNullAndCartNotExists_thenThrownEntityNotFoundException() {
        when(cartGateway.findById(params.getCartId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addProductsToCartUseCase.execute(params));

        verify(cartGateway, never()).save(any(Cart.class));
    }
}
