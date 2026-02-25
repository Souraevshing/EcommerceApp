package com.ecommerce.product.exceptions;

import com.ecommerce.product.dto.ResponseDto;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> details = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err -> details.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(ResponseDto.error("ValidationError", details));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleDeserialization(
            HttpMessageNotReadableException ex) {

        Map<String, String> details = new HashMap<>();
        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof InvalidFormatException ife) {
            String field = extractFieldName(ife);
            details.put(field, "Invalid value");
            return bad(details, "DeserializationError");
        }

        if (cause instanceof MismatchedInputException mie) {
            String field = extractFieldName(mie);
            details.put(field, "Invalid structure");
            return bad(details, "DeserializationError");
        }

        if (cause instanceof JsonMappingException jme) {
            String field = extractFieldName(jme);
            details.put(field, "Invalid field format");
            return bad(details, "DeserializationError");
        }

        details.put("request", "Malformed JSON");
        return bad(details, "MalformedJson");
    }

    private String extractFieldName(JsonMappingException ex) {
        if (ex.getPath() != null && !ex.getPath().isEmpty()) {
            return ex.getPath().get(0).getFieldName();
        }
        return "request";
    }

    private ResponseEntity<ResponseDto<Map<String, String>>> bad(
            Map<String, String> details,
            String error) {

        return ResponseEntity
                .badRequest()
                .body(ResponseDto.error(error, details));
    }
}