package com.biblioteca.erp_biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Health Check", description = "API para verificação de saúde do sistema")
public class HomeController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/health")
    @Operation(
        summary = "Verifica status do sistema",
        description = "Endpoint para verificar se o sistema está operacional"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sistema operacional"),
        @ApiResponse(responseCode = "500", description = "Sistema com problemas")
    })
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("api", "UP");
        
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            status.put("database", "UP");
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            status.put("database", "DOWN");
            status.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(status);
        }
    }
}