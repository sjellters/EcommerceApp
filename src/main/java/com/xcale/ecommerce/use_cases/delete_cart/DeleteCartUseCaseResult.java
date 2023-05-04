package com.xcale.ecommerce.use_cases.delete_cart;

import com.xcale.ecommerce.use_cases.UseCaseResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeleteCartUseCaseResult extends UseCaseResult {

    private String successMessage;
}
