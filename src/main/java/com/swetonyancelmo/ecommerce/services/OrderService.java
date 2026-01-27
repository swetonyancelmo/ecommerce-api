package com.swetonyancelmo.ecommerce.services;

import com.swetonyancelmo.ecommerce.dtos.OrderCreateDTO;
import com.swetonyancelmo.ecommerce.exceptions.BusinessException;
import com.swetonyancelmo.ecommerce.models.Order;
import com.swetonyancelmo.ecommerce.models.OrderItem;
import com.swetonyancelmo.ecommerce.models.Product;
import com.swetonyancelmo.ecommerce.repository.OrderRepository;
import com.swetonyancelmo.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order create(OrderCreateDTO dto) {
        Order order = new Order();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderCreateDTO.OrderItemDTO itemDto : dto.items()) {
            Product product = productService.findById(itemDto.productId());

            if (itemDto.quantity() <= 0) {
                throw new BusinessException("Quantidade deve ser maior que zero");
            }

            if (product.getQuantity() < itemDto.quantity()) {
                throw new BusinessException("Estoque insuficiente para o produto: " + product.getNameProduct());
            }

            BigDecimal unitPrice = product.getPrice();
            BigDecimal quantity = BigDecimal.valueOf(itemDto.quantity());
            BigDecimal subtotal = unitPrice.multiply(quantity);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setUnitPrice(unitPrice);
            orderItem.setOrder(order);

            order.getItems().add(orderItem);

            product.setQuantity(product.getQuantity() - itemDto.quantity());
            productRepository.save(product);

            total = total.add(subtotal);
        }

        order.setTotalPrice(total);

        Order saved = repository.save(order);

        return saved;
    }
}
