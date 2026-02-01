package com.ecommerce.app.services;

import com.ecommerce.app.dto.OrderResponseDto;
import com.ecommerce.app.dto.ResponseDto;

public interface OrderService {
    ResponseDto<OrderResponseDto> createOrder(Long userId);
}
