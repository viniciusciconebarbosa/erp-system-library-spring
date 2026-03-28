package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.config.RabbitMQConfig;
import com.biblioteca.erp_biblioteca.dto.MessageBrokerDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendNotification(MessageBrokerDTO request) {
    System.out.println(">>> TENTANDO ENVIAR PARA EXCHANGE: " + RabbitMQConfig.NOTIFICATION_EXCHANGE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_EXCHANGE,
            RabbitMQConfig.ROUTING_KEY, 
            request
        );
        System.out.println("Mensagem enviada para o RabbitMQ: " + request.getSubject());
    }
}