package com.ecommerce.order.services;

import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.dto.ResponseDto;

public interface OrderService {
    ResponseDto<OrderResponseDto> createOrder(Long userId);
}
