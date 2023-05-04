package com.xcale.ecommerce.use_cases.get_cart;

import com.xcale.ecommerce.enums.EntityType;
import com.xcale.ecommerce.exceptions.EntityNotFoundException;
import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.CartGateway;
import com.xcale.ecommerce.use_cases.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCartUseCase extends UseCase<GetCartUseCaseParams, GetCartUseCaseResult> {

    private final CartGateway cartGateway;

    @Override
    public GetCartUseCaseResult execute(GetCartUseCaseParams params) throws Exception{
        Cart cart = cartGateway
                .findById(params.getCartId())
                .orElseThrow(() -> new EntityNotFoundException(EntityType.CART, params.getCartId()));

        return new GetCartUseCaseResult(cart);
    }
}
