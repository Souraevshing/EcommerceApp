package com.ecommerce.app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponseDto {
    private Long id;
    private UserResponseDto user;
    private ProductResponseDto product;
    private Integer quantity;
    private BigDecimal price;
}
