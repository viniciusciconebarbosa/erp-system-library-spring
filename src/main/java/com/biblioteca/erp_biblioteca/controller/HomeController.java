package com.biblioteca.erp_biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            status.put("database", "UP");
            status.put("api", "UP");
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            status.put("database", "DOWN");
            status.put("api", "UP");
            status.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(status);
        }
    }
}