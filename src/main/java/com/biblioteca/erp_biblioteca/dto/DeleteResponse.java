package com.biblioteca.erp_biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteResponse {
    private String message;
    private String deletedId;
    private String name;
}