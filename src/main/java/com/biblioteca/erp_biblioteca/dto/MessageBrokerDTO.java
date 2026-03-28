package com.biblioteca.erp_biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageBrokerDTO {
    
    private String appId;
    private String recipientEmail;
    private String recipientName;
    private String subject;
    private String content;
    private boolean useAI;
}