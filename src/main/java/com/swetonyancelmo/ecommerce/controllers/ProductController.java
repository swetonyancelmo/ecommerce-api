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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Sessão de Produtos", description = "Endpoints de CRUD de Produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper mapper;

    @GetMapping
    @Operation(summary = "Retorna lista de Produtos", description = "Retorna todos os Produtos cadastrados")
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
    @Operation(summary = "Retorna um Produto por ID", description = "Retorna um Produto por ID")
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
    @Operation(summary = "Cadastra um Produto", description = "Cadastra um novo Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar produto")
    })
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductCreateDTO dto) {
        Product productSaved = productService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToResponseToDto(productSaved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um Produto", description = "Atualiza um Produto por ID")
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
    @Operation(summary = "Deleta um Produto", description = "Deleta um Produto por ID")
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
