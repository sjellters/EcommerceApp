package com.xcale.ecommerce.use_cases.add_products_to_cart;

import com.xcale.ecommerce.models.Product;
import com.xcale.ecommerce.use_cases.UseCaseParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddProductsToCartUseCaseParams extends UseCaseParams {

    private String cartId;
    private List<Product> products;
}
