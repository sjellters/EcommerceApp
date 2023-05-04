package com.xcale.ecommerce.driven_adapters.jpa_repository;

import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.CartGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CartGatewayImpl implements CartGateway {

    private final CartJPARepository cartRepository;

    @Override
    public Cart save(Cart cart) {
        CartJPA cartJPA = CartJPA.fromEntity(cart);
        cartJPA = cartRepository.save(cartJPA);

        return CartJPA.toEntity(cartJPA);
    }

    @Override
    public Optional<Cart> findById(String id) {
        Optional<CartJPA> cartJPA = cartRepository.findById(id);

        return cartJPA.map(CartJPA::toEntity);
    }

    @Override
    public void delete(Cart cart) {
        CartJPA cartJPA = CartJPA.fromEntity(cart);

        cartRepository.delete(cartJPA);
    }

    @Override
    public List<Cart> findAll() {
        List<CartJPA> cartJPAList = (List<CartJPA>) cartRepository.findAll();

        return cartJPAList.stream().map(CartJPA::toEntity).collect(Collectors.toList());
    }
}
