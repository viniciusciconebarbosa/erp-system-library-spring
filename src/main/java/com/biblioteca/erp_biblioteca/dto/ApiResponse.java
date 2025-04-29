package com.biblioteca.erp_biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private String id;
    private String type;
    private Object data;

    public ApiResponse(String message, String id, String type) {
        this.message = message;
        this.id = id;
        this.type = type;
    }
}