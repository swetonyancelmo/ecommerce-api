package com.swetonyancelmo.ecommerce.repository;

import com.swetonyancelmo.ecommerce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
