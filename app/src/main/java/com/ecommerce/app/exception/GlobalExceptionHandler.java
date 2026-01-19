package com.ecommerce.app.exception;

import com.ecommerce.app.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<Void>> handleEnumErrors(HttpMessageNotReadableException httpMessageNotReadableException) {
        String message = "Invalid request payload";

        if (httpMessageNotReadableException.getMostSpecificCause() != null &&
                httpMessageNotReadableException.getMostSpecificCause().getMessage().contains("UserRoles")) {
            message = "Invalid role. Allowed values are ADMIN or CUSTOMER";
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.error(message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleValidationError(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();

        methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    errors.put(error.getField(), error.getDefaultMessage());
                });

        return ResponseEntity
                .badRequest()
                .body(ResponseDto.error("Validation errors", errors));
    }
}
