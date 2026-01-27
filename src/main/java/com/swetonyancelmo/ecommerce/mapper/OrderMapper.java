package com.swetonyancelmo.ecommerce.mapper;

import com.swetonyancelmo.ecommerce.dtos.OrderResponseDTO;
import com.swetonyancelmo.ecommerce.models.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponseDTO convertToDto(Order order) {
        var items = order.getItems()
                .stream()
                .map(item -> new OrderResponseDTO.OrderItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getNameProduct(),
                        item.getUnitPrice(),
                        item.getQuantity(),
                        item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                ))
                .collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getId(),
                items,
                order.getTotalPrice()
        );
    }
}
