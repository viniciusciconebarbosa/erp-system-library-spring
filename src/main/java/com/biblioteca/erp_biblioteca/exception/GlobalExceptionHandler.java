package com.biblioteca.erp_biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String mensagem = "O corpo da requisição está ausente ou mal formatado";
        
        if (ex.getMessage().contains("Required request body is missing")) {
            mensagem = "O corpo da requisição é obrigatório";
        }

        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            mensagem,
            LocalDateTime.now(),
            Map.of("detail", "Verifique se o JSON enviado está correto e todos os campos obrigatórios estão presentes")
        );
        
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("supportedMethods", String.join(", ", ex.getSupportedMethods()));
        details.put("currentMethod", ex.getMethod());

        ApiError apiError = new ApiError(
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            String.format("O método '%s' não é suportado para este endpoint. Métodos suportados: %s",
                ex.getMethod(),
                String.join(", ", ex.getSupportedMethods())),
            LocalDateTime.now(),
            details
        );
        
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação",
            LocalDateTime.now(),
            errors
        );
        
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(BusinessException ex) {
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            LocalDateTime.now(),
            null
        );
        
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro interno do servidor",
            LocalDateTime.now(),
            null
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
