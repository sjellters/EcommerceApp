package com.xcale.ecommerce.use_cases.add_products_to_cart;

import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.use_cases.UseCaseResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddProductsToCartUseCaseResult extends UseCaseResult {

    private Cart cart;
}
