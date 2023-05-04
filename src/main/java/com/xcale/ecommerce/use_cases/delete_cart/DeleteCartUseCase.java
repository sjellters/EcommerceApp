package com.xcale.ecommerce.use_cases.delete_cart;

import com.xcale.ecommerce.enums.EntityType;
import com.xcale.ecommerce.exceptions.EntityNotFoundException;
import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.CartGateway;
import com.xcale.ecommerce.use_cases.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteCartUseCase extends UseCase<DeleteCartUseCaseParams, DeleteCartUseCaseResult> {

    private final CartGateway cartGateway;

    @Override
    public DeleteCartUseCaseResult execute(DeleteCartUseCaseParams params) {
        Cart cart = cartGateway
                .findById(params.getCartId())
                .orElseThrow(() -> new EntityNotFoundException(EntityType.CART, params.getCartId()));

        cartGateway.delete(cart);

        return new DeleteCartUseCaseResult("Cart with id " + params.getCartId() + " was deleted successfully");
    }
}
