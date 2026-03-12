package com.ecommerce.product.services.impl;

import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.dto.ResponseDto;
import com.ecommerce.product.entities.Product;
import com.ecommerce.product.mappers.ProductMapper;
import com.ecommerce.product.repositories.ProductRepository;
import com.ecommerce.product.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<Product> productFound = productRepository.findById(id);

        if(productFound.isEmpty()) {
            return ResponseDto.error("Product not found");
        }

        Product product = productFound.get();

        ProductMapper.updateEntity(product, productRequestDto);

        Product savedProduct = productRepository.save(product);
        return ResponseDto.success(ProductMapper
                .toDto(savedProduct),
                "Product updated successfully"
        );
    }

    @Override
    public ResponseDto<ProductResponseDto> getProductById(Long id) {
        return productRepository
                .findById(id)
                .map(product -> ResponseDto.success(
                        ProductMapper.toDto(product),
                        "Product fetched successfully"
                ))
                .orElse(ResponseDto.error("Product not found"));
    }

    @Override
    public ResponseDto<Void> deleteProduct(Long id) {
        Optional<Product> productFound = productRepository.findById(id);
        if(productFound.isEmpty()) {
            return ResponseDto.error("Product not found");
        }
        productRepository.deleteById(id);
        return ResponseDto.success("Product deleted successfully");
    }

    @Override
    public ResponseDto<List<ProductResponseDto>> getAllProducts() {
        List<Product> allProducts = productRepository.findByActiveTrue();

        if(allProducts.isEmpty()) {
            return ResponseDto.error("No products found");
        }

        return ResponseDto.success(
                ProductMapper.toDtoList(allProducts),
                "All products fetched successfully");
    }

    @Override
    public ResponseDto<List<ProductResponseDto>> searchProductByName(String name) {
        List<Product> products = productRepository.searchProduct(name);

        if(products.isEmpty()) {
            return ResponseDto.error("No products found");
        }

        return ResponseDto.success(
                ProductMapper.toDtoList(products),
                "Products fetched successfully"
        );
    }
}
