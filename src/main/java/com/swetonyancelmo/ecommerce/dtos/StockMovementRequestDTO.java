package com.swetonyancelmo.ecommerce.dtos;

import com.swetonyancelmo.ecommerce.models.enums.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockMovementRequestDTO(
        @NotNull MovementType movementType,
        @NotNull @Positive Long productId,
        @NotNull Integer quantity
        ) {
}
