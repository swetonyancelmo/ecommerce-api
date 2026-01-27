package com.swetonyancelmo.ecommerce.controllers;

import com.swetonyancelmo.ecommerce.dtos.OrderCreateDTO;
import com.swetonyancelmo.ecommerce.dtos.OrderResponseDTO;
import com.swetonyancelmo.ecommerce.mapper.OrderMapper;
import com.swetonyancelmo.ecommerce.models.Order;
import com.swetonyancelmo.ecommerce.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "Order management endpoints")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Created order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar pedido")
    })
    public ResponseEntity<OrderResponseDTO> create(@RequestBody @Valid OrderCreateDTO dto) {
        Order orderCreated = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToDto(orderCreated));
    }
}
