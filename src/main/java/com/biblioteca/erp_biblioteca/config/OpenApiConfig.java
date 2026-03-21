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

    @Value("${app.api-base-url:http://localhost:8080}")
    private String apiBaseUrl;

    @Bean
    public OpenAPI customOpenAPI() {

        Server server = new Server()
                .url(apiBaseUrl)
                .description("prod".equals(activeProfile) ? "Servidor de Produção" : "Servidor Local");

        return new OpenAPI()
                .info(new Info()
                        .title("API ERP Biblioteca")
                        .version("1.0")
                        .description("API REST para gerenciamento de biblioteca, permitindo cadastro de livros, " +
                                "controle de locações e gestão de usuários.")
                        .contact(new Contact()
                                .name("Biblioteca ERP")
                                .email("contato@biblioteca.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(server));
    }
}