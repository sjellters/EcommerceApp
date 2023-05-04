package com.xcale.ecommerce.entry_points.scheduled_jobs;

import com.xcale.ecommerce.driven_adapters.jpa_repository.CartJPA;
import com.xcale.ecommerce.driven_adapters.jpa_repository.CartJPARepository;
import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.use_cases.delete_cart_due_to_inactivity.DeleteCartDueToInactivityUseCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeleteCartDueToInactivityJobTest {

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private DeleteCartDueToInactivityUseCase deleteCartDueToInactivityUseCase;

    @Test
    @Order(1)
    public void shouldDeleteInactiveCart() {
        // Create a cart with an old modification date
        Cart cart = Cart.builder()
                .id(UUID.randomUUID().toString())
                .modificationDate(Instant.now().minus(1, ChronoUnit.DAYS))
                .build();

        cartJPARepository.save(CartJPA.fromEntity(cart));

        // Run the job
        DeleteCartDueToInactivityJob job = new DeleteCartDueToInactivityJob(deleteCartDueToInactivityUseCase);
        job.scheduleTaskWithFixedRate();

        // Verify that the cart was deleted
        assertEquals(cartJPARepository.findById(cart.getId()), Optional.empty());
    }

    @Test
    @Order(2)
    public void shouldNotDeleteActiveCart() {
        // Create a cart with a recent modification date
        Cart cart = Cart.builder()
                .id(UUID.randomUUID().toString())
                .modificationDate(Instant.now())
                .build();

        cartJPARepository.save(CartJPA.fromEntity(cart));

        // Run the job
        DeleteCartDueToInactivityJob job = new DeleteCartDueToInactivityJob(deleteCartDueToInactivityUseCase);
        job.scheduleTaskWithFixedRate();

        // Verify that the cart was not deleted
        assertNotNull(cartJPARepository.findById(cart.getId()));
    }
}
