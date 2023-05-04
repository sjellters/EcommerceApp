package com.xcale.ecommerce.use_cases.add_products_to_cart;

import com.xcale.ecommerce.enums.EntityType;
import com.xcale.ecommerce.exceptions.EntityNotFoundException;
import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.CartGateway;
import com.xcale.ecommerce.use_cases.UseCase;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class AddProductsToCartUseCase extends UseCase<AddProductsToCartUseCaseParams, AddProductsToCartUseCaseResult> {

    private final CartGateway cartGateway;

    @Override
    public AddProductsToCartUseCaseResult execute(AddProductsToCartUseCaseParams params) {
        Cart cart = new Cart();

        if (Objects.nonNull(params.getCartId())) {
            Optional<Cart> existingCart = cartGateway.findById(params.getCartId());

            if (existingCart.isEmpty()) {
                throw new EntityNotFoundException(EntityType.CART, params.getCartId());
            } else {
                cart = existingCart.get();
            }
        } else {
            cart.setId(UUID.randomUUID().toString());
        }

        cart.getProducts().addAll(params.getProducts());
        cart.setModificationDate(Instant.now());

        cart = cartGateway.save(cart);

        return new AddProductsToCartUseCaseResult(cart);
    }
}
