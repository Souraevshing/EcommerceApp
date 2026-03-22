package com.ecommerce.order.services.impl;

import com.ecommerce.order.clients.ProductServiceClient;
import com.ecommerce.order.clients.UserServiceClient;
import com.ecommerce.order.dto.*;
import com.ecommerce.order.entities.CartItem;
import com.ecommerce.order.mappers.CartItemMapper;
import com.ecommerce.order.repositories.CartItemRepository;
import com.ecommerce.order.services.CartItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    @Override
    @CircuitBreaker(name = "product", fallbackMethod = "addToCartFallback")
    @Retry(name = "product", fallbackMethod = "addToCartFallback")
    public ResponseDto<CartItemResponseDto> addToCart(CartItemRequestDto cartItemRequestDto, Long userId) {
        ProductResponseDto productDetails =  productServiceClient
                .getProductDetails(cartItemRequestDto.getProductId())
                .getData();

        UserResponseDto userDetails = userServiceClient
                .getUserDetails(userId)
                .getData();

        if(userDetails == null) {
            return ResponseDto.error("User not found");
        }

        if(productDetails == null) {
            return ResponseDto.error("No product found with the given id");
        }

        if(productDetails.getStockQuantity() < cartItemRequestDto.getQuantity()) {
            return ResponseDto.error("Product out of stock");
        }

        CartItem existingItems = cartItemRepository.findByUserIdAndProductId(userId, cartItemRequestDto.getProductId());
        if(existingItems != null) {
            existingItems.setQuantity(existingItems.getQuantity());
            existingItems.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingItems);
            return ResponseDto.success(CartItemMapper.toDto(existingItems), "Cart item updated successfully");
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequestDto.getProductId());
            cartItem.setQuantity(cartItemRequestDto.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
            return ResponseDto.success(CartItemMapper.toDto(cartItem), "Cart item added successfully");
        }
    }

    public ResponseDto<CartItemResponseDto> addToCartFallback(CartItemRequestDto cartItemRequestDto, Long userId, Throwable throwable) {
        throwable.printStackTrace();
        return ResponseDto.error("Failed to add product to cart for user " + userId);
    }

    @Override
    @CircuitBreaker(name = "product", fallbackMethod = "removeFromCartFallback")
    @Retry(name = "product")
    public ResponseDto<Void> removeFromCart(Long productId, Long userId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if(cartItem == null) {
            return ResponseDto.error("Cart item not found");
        }

        cartItemRepository.delete(cartItem);
        return ResponseDto.success("Cart item removed successfully");
    }

    public ResponseDto<Void> removeFromCartFallback(Long productId, Long userId, Throwable throwable) {
        throwable.printStackTrace();
        return ResponseDto.error("Failed to remove product from cart for user " + userId);
    }

    @Override
    public ResponseDto<List<CartItemResponseDto>> getCartItems(Long userId) {
        List<CartItem> items =  cartItemRepository.findByUserId(userId);

        if(items.isEmpty()) {
            return ResponseDto.error("Cart is empty");
        }

        return ResponseDto.success(items.stream().map(CartItemMapper::toDto).toList(), "Cart items fetched successfully");
    }

    @Override
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
        ResponseDto.success("Cart cleared successfully", null);
    }
}
