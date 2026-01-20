package com.ecommerce.app.services.impl;

import com.ecommerce.app.dto.ProductRequestDto;
import com.ecommerce.app.dto.ProductResponseDto;
import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.entities.Product;
import com.ecommerce.app.mapper.ProductMapper;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ResponseDto<ProductResponseDto> createProduct(ProductRequestDto product) {
        Product savedProduct = productRepository.save(ProductMapper.toEntity(product));
        return ResponseDto.success(ProductMapper.toDto(savedProduct), "Product created successfully");
    }
}
