package com.swetonyancelmo.ecommerce.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductCreateDTO(
        @NotBlank String nameProduct,
        String description,
        @NotNull @Positive BigDecimal price,
        @NotNull Integer quantity
        ) {}
