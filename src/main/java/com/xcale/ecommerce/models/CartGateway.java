package com.xcale.ecommerce.models;

import java.util.List;
import java.util.Optional;

public interface CartGateway {

    Cart save(Cart cart);

    Optional<Cart> findById(String id);

    void delete(Cart cart);

    List<Cart> findAll();
}
