package com.xcale.ecommerce.entry_points.api_rest.controllers;

import com.xcale.ecommerce.entry_points.api_rest.dtos.CartDTO;
import com.xcale.ecommerce.entry_points.api_rest.mappers.UseCaseParamsMapper;
import com.xcale.ecommerce.entry_points.api_rest.mappers.UseCaseResultMapper;
import com.xcale.ecommerce.entry_points.api_rest.response.AppResponse;
import com.xcale.ecommerce.entry_points.api_rest.response.CustomizedResponse;
import com.xcale.ecommerce.exceptions.EntityNotFoundException;
import com.xcale.ecommerce.use_cases.add_products_to_cart.AddProductsToCartUseCase;
import com.xcale.ecommerce.use_cases.add_products_to_cart.AddProductsToCartUseCaseParams;
import com.xcale.ecommerce.use_cases.add_products_to_cart.AddProductsToCartUseCaseResult;
import com.xcale.ecommerce.use_cases.delete_cart.DeleteCartUseCase;
import com.xcale.ecommerce.use_cases.delete_cart.DeleteCartUseCaseParams;
import com.xcale.ecommerce.use_cases.delete_cart.DeleteCartUseCaseResult;
import com.xcale.ecommerce.use_cases.get_cart.GetCartUseCase;
import com.xcale.ecommerce.use_cases.get_cart.GetCartUseCaseParams;
import com.xcale.ecommerce.use_cases.get_cart.GetCartUseCaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final UseCaseParamsMapper useCaseParamsMapper;
    private final UseCaseResultMapper useCaseResultMapper;

    private final GetCartUseCase getCartUseCase;
    private final AddProductsToCartUseCase addProductsToCartUseCase;
    private final DeleteCartUseCase deleteCartUseCase;

    @Operation(summary = "Get a cart by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomizedResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomizedResponse.class))})
    })
    @GetMapping("/{id}")
    public AppResponse<CartDTO> getCart(@PathVariable String id) {
        GetCartUseCaseParams params = useCaseParamsMapper.toGetCartUseCaseParams(id);

        try {
            GetCartUseCaseResult result = getCartUseCase.execute(params);

            return new AppResponse<>(HttpStatus.OK, useCaseResultMapper.toGetCartResponse(result));
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new AppResponse<>(HttpStatus.NOT_FOUND, e.getMessage());
            } else {
                return new AppResponse<>(HttpStatus.CONFLICT, e.getMessage());
            }
        }
    }

    @Operation(summary = "Create or modify a cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart created or modified"),
            @ApiResponse(responseCode = "404", description = "Cart not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomizedResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomizedResponse.class))})
    })
    @PostMapping("/add-products")
    public AppResponse<CartDTO> addProductsToCart(@RequestBody @Validated CartDTO cartDTO) {
        AddProductsToCartUseCaseParams params = useCaseParamsMapper.toAddProductsToCartUseCaseParams(cartDTO);

        try {
            AddProductsToCartUseCaseResult result = addProductsToCartUseCase.execute(params);

            return new AppResponse<>(HttpStatus.OK, useCaseResultMapper.toAddProducts(result));
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new AppResponse<>(HttpStatus.NOT_FOUND, e.getMessage());
            } else {
                return new AppResponse<>(HttpStatus.CONFLICT, e.getMessage());
            }
        }
    }

    @Operation(summary = "Deletes a cart by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomizedResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Cart not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomizedResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomizedResponse.class))})
    })
    @DeleteMapping("{id}")
    public AppResponse<String> deleteCart(@PathVariable String id) {
        DeleteCartUseCaseParams params = useCaseParamsMapper.toDeleteCartUseCaseParams(id);

        try {
            DeleteCartUseCaseResult result = deleteCartUseCase.execute(params);

            return new AppResponse<>(HttpStatus.OK, useCaseResultMapper.toDeleteCartResponse(result));
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new AppResponse<>(HttpStatus.NOT_FOUND, e.getMessage());
            } else {
                return new AppResponse<>(HttpStatus.CONFLICT, e.getMessage());
            }
        }
    }
}
