package com.xcale.ecommerce.config;

import com.xcale.ecommerce.models.CartGateway;
import com.xcale.ecommerce.use_cases.add_products_to_cart.AddProductsToCartUseCase;
import com.xcale.ecommerce.use_cases.delete_cart.DeleteCartUseCase;
import com.xcale.ecommerce.use_cases.delete_cart_due_to_inactivity.DeleteCartDueToInactivityUseCase;
import com.xcale.ecommerce.use_cases.get_cart.GetCartUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean(value = "getCartUseCase")
    public GetCartUseCase getCartUseCase(CartGateway cartGateway) {
        return new GetCartUseCase(cartGateway);
    }

    @Bean(value = "addProductToCartUseCase")
    public AddProductsToCartUseCase addProductToCartUseCase(CartGateway cartGateway) {
        return new AddProductsToCartUseCase(cartGateway);
    }

    @Bean(value = "deleteCartUseCase")
    public DeleteCartUseCase deleteCartUseCase(CartGateway cartGateway) {
        return new DeleteCartUseCase(cartGateway);
    }

    @Bean(value = "deleteCartDueToInactivityUseCase")
    public DeleteCartDueToInactivityUseCase deleteCartDueToInactivityUseCase(CartGateway cartGateway) {
        return new DeleteCartDueToInactivityUseCase(cartGateway);
    }
}
