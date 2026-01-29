package com.ecommerce.app.repository;

import com.ecommerce.app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
