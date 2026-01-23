package com.swetonyancelmo.ecommerce.services;

import com.swetonyancelmo.ecommerce.dtos.StockMovementRequestDTO;
import com.swetonyancelmo.ecommerce.dtos.StockMovementResponseDTO;
import com.swetonyancelmo.ecommerce.exceptions.BusinessException;
import com.swetonyancelmo.ecommerce.exceptions.ResourceNotFoundException;
import com.swetonyancelmo.ecommerce.mapper.StockMovementMapper;
import com.swetonyancelmo.ecommerce.models.Product;
import com.swetonyancelmo.ecommerce.models.StockMovement;
import com.swetonyancelmo.ecommerce.repository.ProductRepository;
import com.swetonyancelmo.ecommerce.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockMovementService {

    @Autowired
    private StockMovementRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private StockMovementMapper mapper;

    public List<StockMovementResponseDTO> findAll(){
        return repository.findAll()
                .stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    public StockMovementResponseDTO findById(Long movementId){
        return repository.findById(movementId)
                .map(mapper::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Movimento de estoque não encontrado com o ID: " + movementId));
    }

    @Transactional
    public StockMovementResponseDTO createStockMovement(StockMovementRequestDTO dto) {
        Product product = productService.findById(dto.productId());

        switch (dto.movementType()){
            case PURCHASE -> addStock(product, dto.quantity());
            case SALE -> removedStock(product, dto.quantity());
        }

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setMovementType(dto.movementType());
        movement.setQuantity(dto.quantity());
        movement.setCreatedAt(LocalDateTime.now());

        StockMovement movementSaved = repository.save(movement);

        return mapper.convertToDto(movementSaved);
    }

    public void addStock(Product product, Integer quantity) {
        if(quantity <= 0){
            throw new BusinessException("Quantidade deve ser maior que zero");
        }

        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);
    }

    public void removedStock(Product product, Integer quantity) {
        if(quantity <= 0){
            throw new BusinessException("Quantidade deve ser maior que zero");
        }

        if(product.getQuantity() < quantity) {
            throw new BusinessException("Estoque insuficiente");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }
}
