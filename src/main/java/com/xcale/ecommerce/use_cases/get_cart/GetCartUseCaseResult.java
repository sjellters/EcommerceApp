package com.xcale.ecommerce.use_cases.get_cart;

import com.xcale.ecommerce.models.Cart;
import com.xcale.ecommerce.use_cases.UseCaseResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetCartUseCaseResult extends UseCaseResult {

    private Cart cart;
}
