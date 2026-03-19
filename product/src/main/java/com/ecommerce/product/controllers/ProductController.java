package com.ecommerce.product.controllers;

import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.dto.ResponseDto;
import com.ecommerce.product.services.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductServiceImpl productService;

    @PostMapping("/products")
    public ResponseEntity<ResponseDto<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        log.info("Request received to create product");
        ResponseDto<ProductResponseDto> response = productService.createProduct(productRequestDto);
        log.info("Successfully created product with id: {}", response.getData().getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/products/{id}")
    public  ResponseEntity<ResponseDto<ProductResponseDto>> updateProduct(@Valid @RequestBody ProductRequestDto productRequestDto, @PathVariable Long id) {
        log.info("Request received to update product with id {}", id);
        ResponseDto<ProductResponseDto> response = productService.updateProduct(productRequestDto, id);

        if(response.getError() != null) {
            log.warn("Failed to update product with id: {} - {}", id, response.getError());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        log.info("Successfully updated product with id: {}", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ResponseDto<ProductResponseDto>> getProductById(@PathVariable Long id){
        log.info("Request received to fetch product with id {}",id);
        ResponseDto<ProductResponseDto> response = productService.getProductById(id);

        if(response.getData() != null){
            log.info("Successfully fetched product with id: {}", id);
            return ResponseEntity.ok(response);
        }

        log.warn("Product not found with id: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/products")
    public ResponseEntity<ResponseDto<List<ProductResponseDto>>> getAllProducts(){
        log.info("Request received to fetch all products");
        ResponseDto<List<ProductResponseDto>> products = productService.getAllProducts();

        if(products.getData() == null || products.getData().isEmpty()){
            log.warn("No products found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(products);
        }

        log.info("Successfully fetched {} products",products.getData().size());
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteProduct(@PathVariable Long id) {
        log.info("Request received to delete product with id {}", id);
        ResponseDto<Void> response = productService.deleteProduct(id);

        if (response.getError() != null) {
            log.warn("Failed to delete product with id: {} - {}", id, response.getError());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        log.info("Successfully deleted product with id: {}", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/search")
    public ResponseEntity<ResponseDto<List<ProductResponseDto>>> searchProduct(
            @RequestParam String query) {
        log.info("Request received to search products by name: {}", query);
        ResponseDto<List<ProductResponseDto>> response =
                productService.searchProductByName(query);

        if (response.getData() == null || response.getData().isEmpty()) {
            log.warn("No products found with name: {}", query);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        log.info("Successfully searched products by name: {}", query);
        return ResponseEntity.ok(response);
    }
}
