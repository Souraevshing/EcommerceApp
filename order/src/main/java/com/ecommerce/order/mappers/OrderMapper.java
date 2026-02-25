package com.ecommerce.order.mappers;

import com.ecommerce.order.dto.OrderItemDto;
import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.entities.Order;

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
                                item.getProductId(),
                                item.getQuantity(),
                                item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }
}
