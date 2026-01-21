package com.swetonyancelmo.ecommerce.mapper;

import com.swetonyancelmo.ecommerce.dtos.ProductResponseDTO;
import com.swetonyancelmo.ecommerce.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO convertToResponseToDto(Product product){
        return new ProductResponseDTO(
                product.getId(),
                product.getNameProduct(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity()
        );
    }
}
