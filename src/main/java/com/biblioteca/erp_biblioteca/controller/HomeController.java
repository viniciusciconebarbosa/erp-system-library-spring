package com.biblioteca.erp_biblioteca.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "Home", description = "Endpoints de status da API")
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "redirect:/swagger-ui.html";
    }
    
    @GetMapping("/health")
    @ResponseBody
    @Operation(
        summary = "Verificação de saúde",
        description = "Endpoint para health check da API"
    )
    public String health() {
        return "OK";
    }
}