package com.ecommerce.app.services.impl;

import com.ecommerce.app.dto.CartItemResponseDto;
import com.ecommerce.app.dto.OrderResponseDto;
import com.ecommerce.app.dto.OrderStatus;
import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.entities.Order;
import com.ecommerce.app.entities.OrderItem;
import com.ecommerce.app.entities.Product;
import com.ecommerce.app.entities.Users;
import com.ecommerce.app.mapper.OrderMapper;
import com.ecommerce.app.repository.OrderRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartItemServiceImpl cartItemService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseDto<OrderResponseDto> createOrder(Long userId) {
        ResponseDto<List<CartItemResponseDto>> cartItems = cartItemService.getCartItems(userId);
        if(cartItems.getData() == null || cartItems.getData().isEmpty()) {
            return ResponseDto.error("Cart is empty");
        }

        Optional<Users> users = userRepository.findById(userId);
        if(users.isEmpty()) {
            return ResponseDto.error("User not found");
        }

        Users usersFound = users.get();

        List<CartItemResponseDto> cartItemsFound = cartItems.getData();

        BigDecimal totalPrice = cartItemsFound
                .stream()
                .map(CartItemResponseDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setStatus(OrderStatus.CONFIRMED);
        order.setUsers(usersFound);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItemsFound
                .stream()
                .map(items -> {
                    Product product = productRepository.findById(items.getProduct().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(items.getQuantity());
                    orderItem.setPrice(items.getPrice());
                    return orderItem;
                })
                .toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartItemService.clearCart(userId);
        return ResponseDto.success(OrderMapper.toDto(savedOrder), "Order created successfully");
    }
}
