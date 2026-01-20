package com.ecommerce.app.controllers;

import com.ecommerce.app.dto.ProductRequestDto;
import com.ecommerce.app.dto.ProductResponseDto;
import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.services.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @PostMapping("/products")
    public ResponseEntity<ResponseDto<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(productRequestDto));
    }

    @PutMapping("/products/{id}")
    public  ResponseEntity<ResponseDto<ProductResponseDto>> updateProduct(@Valid @RequestBody ProductRequestDto productRequestDto, @PathVariable Long id) {
        ResponseDto<ProductResponseDto> response = productService.updateProduct(productRequestDto, id);

        if(response.getError() != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ResponseDto<ProductResponseDto>> getUserById(@PathVariable Long id){
        ResponseDto<ProductResponseDto> response = productService.getProductById(id);
        if(response.getData() != null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/products")
    public ResponseEntity<ResponseDto<List<ProductResponseDto>>> getAllProducts(){
        ResponseDto<List<ProductResponseDto>> products = productService.getAllProducts();

        if(products.getData() == null || products.getData().isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(products);
        }

        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ResponseDto<String>> deleteProduct(@PathVariable Long id) {
        ResponseDto<String> response = productService.deleteProduct(id);

        if (response.getError() != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
