package com.xcale.ecommerce.use_cases.delete_cart_due_to_inactivity;

import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.CartGateway;
import com.xcale.ecommerce.use_cases.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Log
@RequiredArgsConstructor
public class DeleteCartDueToInactivityUseCase extends UseCase<DeleteCartDueToInactivityUseCaseParams, DeleteCartDueToInactivityUseCaseResult> {

    private final CartGateway cartGateway;

    @Override
    public DeleteCartDueToInactivityUseCaseResult execute(DeleteCartDueToInactivityUseCaseParams params) {
        List<Cart> cartList = cartGateway.findAll();

        cartList.forEach(cart -> {
            if (cart.getModificationDate().isBefore(Instant.now().minus(10, ChronoUnit.MINUTES))) {
                log.info("Cart " + cart.getId() + " is inactive. Deleting it.");

                cartGateway.delete(cart);
            }
        });

        return new DeleteCartDueToInactivityUseCaseResult();
    }
}
