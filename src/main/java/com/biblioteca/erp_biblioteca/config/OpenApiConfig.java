package com.biblioteca.erp_biblioteca.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Bean
    public OpenAPI customOpenAPI() {
        Server server;
        
        if ("prod".equals(activeProfile)) {
            server = new Server()
                .url("https://minha1api.duckdns.org")
                .description("Servidor de Produção");
        } else {
            server = new Server()
                .url("http://localhost:8080")
                .description("Servidor Local");
        }

        return new OpenAPI()
                .info(new Info()
                        .title("API ERP Biblioteca Comunitária")
                        .version("1.0")
                        .description("API REST para gerenciamento de biblioteca comunitária, permitindo cadastro de livros, " +
                                "controle de locações e gestão de usuários.")
                        .contact(new Contact()
                                .name("Biblioteca Comunitária")
                                .email("contato@biblioteca.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(server));
    }
}