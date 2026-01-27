package com.ecommerce.app.repository;

import com.ecommerce.app.entities.CartItem;
import com.ecommerce.app.entities.Product;
import com.ecommerce.app.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(Users users, Product product);
    void deleteByUserAndProduct(Users user, Product product);
    List<CartItem> findByUser(Users users);
}
