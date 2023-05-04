package com.xcale.ecommerce.driven_adapters.jpa_repository;

import com.google.gson.Gson;
import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class CartGatewayImplTest {

    @Autowired
    private CartGatewayImpl cartGateway;

    @Autowired
    private CartJPARepository cartJPARepository;

    @AfterEach
    void tearDown() {
        cartJPARepository.deleteAll();
    }

    @Test
    public void saveCartTest() {
        // Given
        String cartId = UUID.randomUUID().toString();
        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .id(1L)
                .description("Product 1")
                .amount(10.0)
                .build());
        products.add(Product.builder()
                .id(2L)
                .description("Product 2")
                .amount(20.0)
                .build());
        Instant now = Instant.now();
        Cart cart = Cart.builder()
                .id(cartId)
                .products(products)
                .modificationDate(now)
                .build();

        // When
        Cart savedCart = cartGateway.save(cart);

        // Then
        assertThat(savedCart.getId()).isEqualTo(cartId);
        assertThat(savedCart.getProducts().size()).isEqualTo(2);
        assertThat(savedCart.getProducts().get(0).getId()).isEqualTo(1L);
        assertThat(savedCart.getProducts().get(0).getDescription()).isEqualTo("Product 1");
        assertThat(savedCart.getProducts().get(0).getAmount()).isEqualTo(10.0);
        assertThat(savedCart.getProducts().get(1).getId()).isEqualTo(2L);
        assertThat(savedCart.getProducts().get(1).getDescription()).isEqualTo("Product 2");
        assertThat(savedCart.getProducts().get(1).getAmount()).isEqualTo(20.0);
        assertThat(savedCart.getModificationDate()).isEqualTo(now);

        Optional<CartJPA> cartJPAOptional = cartJPARepository.findById(cartId);
        assertThat(cartJPAOptional.isPresent()).isTrue();

        assert cartJPAOptional.isPresent();
        CartJPA cartJPA = cartJPAOptional.get();
        assertThat(cartJPA.getId()).isEqualTo(cartId);
        assertThat(cartJPA.getProducts()).isEqualTo("[{\"id\":1,\"description\":\"Product 1\",\"amount\":10.0},{\"id\":2,\"description\":\"Product 2\",\"amount\":20.0}]");
        assertThat(cartJPA.getModificationDate()).isEqualTo(now);
    }

    @Test
    void findById_shouldReturnCart_whenCartExists() {
        // given
        String id = UUID.randomUUID().toString();
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", 10.0),
                new Product(2L, "Product 2", 20.0));
        Instant modificationDate = Instant.now();

        CartJPA cartJPA = new CartJPA();
        cartJPA.setId(id);
        cartJPA.setProducts(new Gson().toJson(products));
        cartJPA.setModificationDate(modificationDate);
        cartJPARepository.save(cartJPA);

        // when
        Optional<Cart> foundCart = cartGateway.findById(id);

        // then
        assertTrue(foundCart.isPresent());
        assertEquals(id, foundCart.get().getId());
        assertEquals(products, foundCart.get().getProducts());
        assertEquals(modificationDate, foundCart.get().getModificationDate());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenCartDoesNotExist() {
        // given
        String id = UUID.randomUUID().toString();

        // when
        Optional<Cart> foundCart = cartGateway.findById(id);

        // then
        assertFalse(foundCart.isPresent());
    }

    @Test
    void delete_shouldDeleteCart_whenCartExists() {
        // given
        String id = UUID.randomUUID().toString();
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", 10.0),
                new Product(2L, "Product 2", 20.0));
        Instant modificationDate = Instant.now();

        CartJPA cartJPA = new CartJPA();
        cartJPA.setId(id);
        cartJPA.setProducts(new Gson().toJson(products));
        cartJPA.setModificationDate(modificationDate);
        cartJPARepository.save(cartJPA);

        // when
        cartGateway.delete(new Cart(id, products, modificationDate));

        // then
        assertFalse(cartJPARepository.findById(id).isPresent());
    }

    @Test
    public void delete_shouldNotThrowException_whenCartDoesNotExist() {
        // Arrange
        String nonExistingCartId = UUID.randomUUID().toString();

        // Act & Assert
        assertDoesNotThrow(() -> cartGateway.delete(Cart.builder().id(nonExistingCartId).build()));
    }

    @Test
    public void findAll_shouldReturnAllCarts() {
        // Given
        List<Cart> cartList = Arrays.asList(
                Cart.builder()
                        .id(UUID.randomUUID().toString())
                        .modificationDate(Instant.now())
                        .products(Collections.singletonList(Product.builder().id(1L).description("Product 1").amount(20.0).build()))
                        .build(),
                Cart.builder()
                        .id(UUID.randomUUID().toString())
                        .modificationDate(Instant.now())
                        .products(Collections.singletonList(Product.builder().id(2L).description("Product 2").amount(30.0).build()))
                        .build(),
                Cart.builder()
                        .id(UUID.randomUUID().toString())
                        .modificationDate(Instant.now())
                        .products(Collections.singletonList(Product.builder().id(3L).description("Product 3").amount(40.0).build()))
                        .build()
        );

        cartList.forEach(cart -> cartGateway.save(cart));

        // When
        List<Cart> retrievedCartList = cartGateway.findAll();

        // Then
        assertEquals(cartList.size(), retrievedCartList.size());
        assertTrue(retrievedCartList.containsAll(cartList));
    }
}
