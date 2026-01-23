package com.swetonyancelmo.ecommerce.dtos;

import com.swetonyancelmo.ecommerce.models.enums.MovementType;

public record StockMovementResponseDTO(
        Long movementId,
        Long productId,
        MovementType movementType,
        Integer quantity
) {
}
