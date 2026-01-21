package com.swetonyancelmo.ecommerce.repository;

import com.swetonyancelmo.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
