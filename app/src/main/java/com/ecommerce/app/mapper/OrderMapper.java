package com.ecommerce.app.mapper;

import com.ecommerce.app.dto.OrderItemDto;
import com.ecommerce.app.dto.OrderResponseDto;
import com.ecommerce.app.entities.Order;

import java.math.BigDecimal;

public class OrderMapper {
    public static OrderResponseDto toDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems()
                        .stream()
                        .map(item -> new OrderItemDto(
                                item.getId(),
                                item.getProduct().getId(),
                                item.getQuantity(),
                                item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }
}
