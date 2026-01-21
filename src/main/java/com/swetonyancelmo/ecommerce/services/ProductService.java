package com.swetonyancelmo.ecommerce.services;

import com.swetonyancelmo.ecommerce.dtos.ProductCreateDTO;
import com.swetonyancelmo.ecommerce.dtos.ProductUpdateDTO;
import com.swetonyancelmo.ecommerce.exceptions.ResourceNotFoundException;
import com.swetonyancelmo.ecommerce.models.Product;
import com.swetonyancelmo.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
    }

    public Product create(ProductCreateDTO dto) {
        Product newProduct = new Product();

        newProduct.setNameProduct(dto.nameProduct());
        newProduct.setDescription(dto.description());
        newProduct.setPrice(dto.price());
        newProduct.setQuantity(dto.quantity());

        return productRepository.save(newProduct);
    }

    public Product update(Long id, ProductUpdateDTO dto) {
        Product productFound = findById(id);

        if(dto.nameProduct() != null && dto.nameProduct().isBlank()) productFound.setNameProduct(dto.nameProduct());
        if(dto.description() != null && dto.description().isBlank()) productFound.setDescription(dto.description());
        if(dto.price() != null) productFound.setPrice(dto.price());
        if(dto.quantity() != null) productFound.setQuantity(dto.quantity());

        return productRepository.save(productFound);
    }

    public void delete(Long id) {
        Product productFound = findById(id);
        productRepository.delete(productFound);
    }
}
