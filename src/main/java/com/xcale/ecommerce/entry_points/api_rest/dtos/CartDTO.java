package com.xcale.ecommerce.entry_points.api_rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    @JsonProperty("cartId")
    private String id;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ProductDTO> products;
}

