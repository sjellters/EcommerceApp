package com.xcale.ecommerce.entry_points.api_rest.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppResponse<T> extends ResponseEntity<CustomizedResponse<T>> {

    public AppResponse(HttpStatus status, T body) {
        super(new CustomizedResponse<>(body, null), status);
    }

    public AppResponse(HttpStatus status, String errorMessage) {
        super(new CustomizedResponse<>(null, errorMessage), status);
    }
}
