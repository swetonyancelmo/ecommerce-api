package com.swetonyancelmo.ecommerce.dtos;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        List<OrderItemResponseDTO> items,
        BigDecimal totalPrice
) {
    public record OrderItemResponseDTO(
            Long productId,
            String productName,
            BigDecimal unitPrice,
            Integer quantity,
            BigDecimal subtotal
    ) {}
}
