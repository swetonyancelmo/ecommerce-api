package com.swetonyancelmo.ecommerce.controllers;

import com.swetonyancelmo.ecommerce.dtos.StockMovementRequestDTO;
import com.swetonyancelmo.ecommerce.dtos.StockMovementResponseDTO;
import com.swetonyancelmo.ecommerce.services.StockMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/stock-movements")
@Tag(name = "Stock Movement", description = "Stock Movements management endpoints")
public class StockMovementController {

    @Autowired
    private StockMovementService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get all movements in stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentos no estoque encontrados"),
            @ApiResponse(responseCode = "400", description = "Erro ao encontrar movimentos")
    })
    public ResponseEntity<List<StockMovementResponseDTO>> getAll(){
        List<StockMovementResponseDTO> movements = service.findAll();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get a movement in stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimento no estoque encontrado"),
            @ApiResponse(responseCode = "404", description = "Movimento não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao encontrar movimento")
    })
    public ResponseEntity<StockMovementResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Create a new movement in stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimento criado no estoque"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar movimento")
    })
    public ResponseEntity<StockMovementResponseDTO> createStockMovement(@RequestBody @Valid StockMovementRequestDTO dto) {
        return ResponseEntity.ok(service.createStockMovement(dto));
    }
}
