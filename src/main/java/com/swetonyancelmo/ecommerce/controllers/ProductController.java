package com.swetonyancelmo.ecommerce.controllers;

import com.swetonyancelmo.ecommerce.dtos.ProductCreateDTO;
import com.swetonyancelmo.ecommerce.dtos.ProductResponseDTO;
import com.swetonyancelmo.ecommerce.dtos.ProductUpdateDTO;
import com.swetonyancelmo.ecommerce.mapper.ProductMapper;
import com.swetonyancelmo.ecommerce.models.Product;
import com.swetonyancelmo.ecommerce.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "200", description = "Nenhum produto cadastrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao retorna produtos")
    })
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> productDtos = productService.findAll()
                .stream()
                .map(mapper::convertToResponseToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao retorna produtos")
    })
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(mapper.convertToResponseToDto(product));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar produto")
    })
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductCreateDTO dto) {
        Product productSaved = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToResponseToDto(productSaved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar produtos")
    })
    public ResponseEntity<ProductResponseDTO> update(@RequestBody @Valid ProductUpdateDTO dto, @PathVariable("id") Long id) {
        Product productUpdated = productService.update(id, dto);
        return ResponseEntity.ok(mapper.convertToResponseToDto(productUpdated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar produtos")
    })
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
