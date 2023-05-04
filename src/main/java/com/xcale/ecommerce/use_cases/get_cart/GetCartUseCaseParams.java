package com.xcale.ecommerce.use_cases.get_cart;

import com.xcale.ecommerce.use_cases.UseCaseParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetCartUseCaseParams extends UseCaseParams {

    private String cartId;
}
