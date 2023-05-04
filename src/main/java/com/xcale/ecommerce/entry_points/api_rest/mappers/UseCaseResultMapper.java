package com.xcale.ecommerce.entry_points.api_rest.mappers;

import com.xcale.ecommerce.entry_points.api_rest.dtos.CartDTO;
import com.xcale.ecommerce.use_cases.add_products_to_cart.AddProductsToCartUseCaseResult;
import com.xcale.ecommerce.use_cases.delete_cart.DeleteCartUseCaseResult;
import com.xcale.ecommerce.use_cases.get_cart.GetCartUseCaseResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UseCaseResultMapper {

    @Mapping(target = ".", source = "result.cart")
    CartDTO toGetCartResponse(GetCartUseCaseResult result);

    @Mapping(target = ".", source = "result.successMessage")
    String toDeleteCartResponse(DeleteCartUseCaseResult result);

    @Mapping(target = ".", source = "result.cart")
    CartDTO toAddProducts(AddProductsToCartUseCaseResult result);
}
