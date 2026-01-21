package com.swetonyancelmo.ecommerce.dtos;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String nameProduct,
        String description,
        BigDecimal price,
        Integer quantity
) {}
