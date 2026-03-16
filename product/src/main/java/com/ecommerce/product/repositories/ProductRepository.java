package com.ecommerce.product.repositories;

import com.ecommerce.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
       SELECT p FROM Product p
       WHERE p.active = true
         AND p.stockQuantity > 0
         AND LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))
       """)
    List<Product> searchProduct(@Param("query") String query);

    Optional<Product> findByIdAndActiveTrue(Long id);

    List<Product> findAllByActiveTrue();
}
