package com.biblioteca.erp_biblioteca.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "ERP Biblioteca API!";
    }
    
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}