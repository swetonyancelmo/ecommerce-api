package com.swetonyancelmo.ecommerce.mapper;

import com.swetonyancelmo.ecommerce.dtos.StockMovementResponseDTO;
import com.swetonyancelmo.ecommerce.models.StockMovement;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper {

    public StockMovementResponseDTO convertToDto(StockMovement stockMovement) {
        return new StockMovementResponseDTO(
                stockMovement.getId(),
                stockMovement.getProduct().getId(),
                stockMovement.getMovementType(),
                stockMovement.getQuantity()
        );
    }
}
