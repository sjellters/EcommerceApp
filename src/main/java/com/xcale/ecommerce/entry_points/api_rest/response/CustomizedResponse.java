package com.xcale.ecommerce.entry_points.api_rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomizedResponse<T> {

    private T data;
    private String errorMessage;
}
