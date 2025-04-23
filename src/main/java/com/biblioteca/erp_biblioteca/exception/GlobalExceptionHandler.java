package com.biblioteca.erp_biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
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
        Map<String, String> detalhes = new HashMap<>();
        String errorMessage = ex.getMessage();
        
        if (errorMessage.contains("UUID")) {
            mensagem = "Formato de UUID inválido";
            detalhes.put("detail", "O ID deve ser um UUID válido, exemplo: 123e4567-e89b-12d3-a456-426614174000");
        } else if (errorMessage.contains("Required request body is missing")) {
            mensagem = "O corpo da requisição é obrigatório";
            detalhes.put("detail", "Verifique se o JSON foi enviado corretamente");
        } else if (errorMessage.contains("EstadoConservacao")) {
            mensagem = "Valor inválido para estado de conservação";
            detalhes.put("detail", "Valores aceitos: NOVO, OTIMO, BOM, REGULAR, RUIM");
        } else if (errorMessage.contains("Genero")) {
            mensagem = "Valor inválido para gênero";
            detalhes.put("detail", "Valores aceitos: FICCAO, NAO_FICCAO, TERROR, ROMANCE, EDUCACAO, TECNICO");
        } else if (errorMessage.contains("ClassificacaoEtaria")) {
            mensagem = "Valor inválido para classificação etária";
            detalhes.put("detail", "Valores aceitos: LIVRE, DOZE_ANOS, QUATORZE_ANOS, DEZESSEIS_ANOS, DEZOITO_ANOS");
        } else {
            detalhes.put("detail", "Verifique se o JSON enviado está correto e todos os campos obrigatórios estão presentes");
        }

        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            mensagem,
            LocalDateTime.now(),
            detalhes
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
        
        // Adiciona a informação sobre o doadorId mesmo quando não há erro
        errors.put("doadorId", "ID do usuário doador (opcional)");

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

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ApiError> handleEmailJaCadastrado(EmailJaCadastradoException ex) {
        ApiError apiError = new ApiError(
            HttpStatus.CONFLICT.value(),
            ex.getMessage(),
            LocalDateTime.now(),
            null
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
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

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex) {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED.value(),
            ex.getMessage(),
            LocalDateTime.now(),
            null
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("detail", "Você não tem permissão para acessar este recurso");
        
        ApiError apiError = new ApiError(
            HttpStatus.FORBIDDEN.value(),
            "Acesso negado",
            LocalDateTime.now(),
            details
        );
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex) {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED.value(),
            "Falha na autenticação: " + ex.getMessage(),
            LocalDateTime.now(),
            null
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }
}
