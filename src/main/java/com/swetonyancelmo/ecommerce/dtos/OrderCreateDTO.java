package com.swetonyancelmo.ecommerce.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderCreateDTO(
        @NotEmpty List<OrderItemDTO> items
) {
    public record OrderItemDTO(
            @NotNull Long productId,
            @NotNull @Positive Integer quantity
    ) {
    }
}
