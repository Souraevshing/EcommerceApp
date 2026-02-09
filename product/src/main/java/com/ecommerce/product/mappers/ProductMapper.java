package com.ecommerce.product.mappers;

import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.entities.Product;

import java.util.List;

public class ProductMapper {
    public static Product toEntity(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setStockQuantity(productRequestDto.getStockQuantity());
        product.setCategory(productRequestDto.getCategory());
        product.setImageUrl(productRequestDto.getImageUrl());
        product.setActive(true);
        return product;
    }

    public static ProductResponseDto toDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setStockQuantity(product.getStockQuantity());
        productResponseDto.setActive(product.getActive());
        productResponseDto.setCategory(product.getCategory());
        productResponseDto.setImageUrl(product.getImageUrl());
        return productResponseDto;
    }

    public static List<ProductResponseDto> toDtoList(List<Product> products) {
        return products
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }
}
