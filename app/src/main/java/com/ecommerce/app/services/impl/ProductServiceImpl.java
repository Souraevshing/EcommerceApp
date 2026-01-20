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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ResponseDto<ProductResponseDto> createProduct(ProductRequestDto productRequestDto) {
        Product savedProduct = productRepository.save(ProductMapper.toEntity(productRequestDto));
        return ResponseDto.success(ProductMapper.toDto(savedProduct), "Product created successfully");
    }

    @Override
    public ResponseDto<ProductResponseDto> updateProduct(ProductRequestDto productRequestDto, Long id) {
        Product product = new Product();
        Optional<Product> productFound = productRepository.findById(id);
        if(productFound.isEmpty()) {
            return ResponseDto.error("Product not found");
        }
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setStockQuantity(productRequestDto.getStockQuantity());
        product.setCategory(productRequestDto.getCategory());
        product.setImageUrl(productRequestDto.getImageUrl());
        Product savedProduct = productRepository.save(product);
        return ResponseDto.success(ProductMapper.toDto(savedProduct), "Product updated successfully");
    }
}
