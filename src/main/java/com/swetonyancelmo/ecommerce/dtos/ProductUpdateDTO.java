package com.swetonyancelmo.ecommerce.dtos;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        String nameProduct,
        String description,
        BigDecimal price,
        Integer quantity
) {}
