package com.ecommerce.order.services.impl;

import com.ecommerce.order.dto.CartItemResponseDto;
import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.dto.OrderStatus;
import com.ecommerce.order.dto.ResponseDto;
import com.ecommerce.order.entities.Order;
import com.ecommerce.order.entities.OrderItem;
import com.ecommerce.order.mappers.OrderMapper;
import com.ecommerce.order.repositories.OrderRepository;
import com.ecommerce.order.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartItemServiceImpl cartItemService;
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @CircuitBreaker(name = "user", fallbackMethod = "createOrderFallback")
    @Retry(name = "user", fallbackMethod = "createOrderFallback")
    public ResponseDto<OrderResponseDto> createOrder(Long userId) {
        ResponseDto<List<CartItemResponseDto>> cartItems = cartItemService.getCartItems(userId);
        if(cartItems.getData() == null || cartItems.getData().isEmpty()) {
            return ResponseDto.error("Cart is empty");
        }

        List<CartItemResponseDto> cartItemsFound = cartItems.getData();

// TODO adding units and quantity to the order items

//        BigDecimal totalPrice = cartItemsFound
//                .stream()
//                .map(CartItemResponseDto::getPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice = cartItemsFound.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setStatus(OrderStatus.CONFIRMED);
        order.setUserId(userId);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItemsFound
                .stream()
                .map(items -> new OrderItem(
                        null,
                            items.getProductId(),
                            items.getQuantity(),
                            items.getPrice(),
                            order
                    ))
                    .toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartItemService.clearCart(userId);

        // send order created event to RabbitMQ
        rabbitTemplate
                .convertAndSend(
                        "order.exchange",
                        "order.tracking",
                        Map.of("orderId", savedOrder.getId(), "status", "CREATED")
                );

        return ResponseDto.success(OrderMapper.toDto(savedOrder), "Order created successfully");
    }

    public ResponseDto<OrderResponseDto> createOrderFallback(Long userId, Throwable throwable) {
        throwable.printStackTrace();
        return ResponseDto.error("Failed to create order for user " + userId);
    }
}
