package com.xcale.ecommerce.exceptions;

import com.xcale.ecommerce.enums.EntityType;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(EntityType entityType, String entityId) {
        super(String.format("Entity of type '%s' with id '%s' not found", entityType, entityId));
    }
}
