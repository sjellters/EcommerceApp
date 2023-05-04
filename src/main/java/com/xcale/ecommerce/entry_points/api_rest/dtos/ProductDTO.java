package com.xcale.ecommerce.entry_points.api_rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotNull
    private long id;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private double amount;
}
