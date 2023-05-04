package com.xcale.ecommerce.entry_points.api_rest.mappers;

import com.xcale.ecommerce.entry_points.api_rest.dtos.CartDTO;
import com.xcale.ecommerce.use_cases.add_products_to_cart.AddProductsToCartUseCaseParams;
import com.xcale.ecommerce.use_cases.delete_cart.DeleteCartUseCaseParams;
import com.xcale.ecommerce.use_cases.get_cart.GetCartUseCaseParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UseCaseParamsMapper {

    @Mapping(target = "cartId", source = "cartId")
    GetCartUseCaseParams toGetCartUseCaseParams(String cartId);

    @Mapping(target = "cartId", source = "cartDTO.id")
    AddProductsToCartUseCaseParams toAddProductsToCartUseCaseParams(CartDTO cartDTO);

    @Mapping(target = "cartId", source = "cartId")
    DeleteCartUseCaseParams toDeleteCartUseCaseParams(String cartId);
}
