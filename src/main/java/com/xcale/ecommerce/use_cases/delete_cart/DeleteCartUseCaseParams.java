package com.xcale.ecommerce.use_cases.delete_cart;

import com.xcale.ecommerce.use_cases.UseCaseParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteCartUseCaseParams extends UseCaseParams {

    private String cartId;
}


