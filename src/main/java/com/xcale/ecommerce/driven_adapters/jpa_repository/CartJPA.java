package com.xcale.ecommerce.driven_adapters.jpa_repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.models.Product;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CartJPA {

    @Id
    private String id;

    private String products;

    private Instant modificationDate;

    public static CartJPA fromEntity(Cart cart) {
        CartJPA cartJPA = new CartJPA();
        cartJPA.setId(cart.getId());
        cartJPA.setModificationDate(cart.getModificationDate());
        cartJPA.setProducts(new Gson().toJson(cart.getProducts()));
        return cartJPA;
    }

    public static Cart toEntity(CartJPA cartJPA) {
        Cart cart = new Cart();
        cart.setId(cartJPA.getId());
        cart.setModificationDate(cartJPA.getModificationDate());

        Type listType = new TypeToken<ArrayList<Product>>() {
        }.getType();
        List<Product> products = new Gson().fromJson(cartJPA.getProducts(), listType);

        cart.setProducts(products);
        return cart;
    }
}
