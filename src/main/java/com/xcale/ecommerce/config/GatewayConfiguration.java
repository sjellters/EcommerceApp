package com.xcale.ecommerce.config;

import com.xcale.ecommerce.driven_adapters.jpa_repository.CartGatewayImpl;
import com.xcale.ecommerce.driven_adapters.jpa_repository.CartJPARepository;
import com.xcale.ecommerce.models.CartGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    private final CartJPARepository cartJPARepository;

    public GatewayConfiguration(CartJPARepository cartJPARepository) {
        this.cartJPARepository = cartJPARepository;
    }

    @Bean(name = "cartGateway")
    public CartGateway cartGateway() {
        return new CartGatewayImpl(cartJPARepository);
    }
}
