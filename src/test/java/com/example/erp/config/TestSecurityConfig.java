package com.example.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/usuarios/**").authenticated()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    
                    Map<String, Object> body = new HashMap<>();
                    body.put("timestamp", new Date());
                    body.put("status", HttpStatus.UNAUTHORIZED.value());
                    body.put("message", "Acesso não autorizado");
                    
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(response.getOutputStream(), body);
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    
                    Map<String, Object> body = new HashMap<>();
                    body.put("timestamp", new Date());
                    body.put("status", HttpStatus.FORBIDDEN.value());
                    body.put("message", "Acesso negado - Permissão insuficiente");
                    
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(response.getOutputStream(), body);
                }));

        return http.build();
    }

    @Bean
    public HandlerExceptionResolver customExceptionResolver() {
        return (request, response, handler, ex) -> {
            if (ex instanceof AuthorizationDeniedException) {
                try {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    
                    Map<String, Object> body = new HashMap<>();
                    body.put("timestamp", new Date());
                    body.put("status", HttpStatus.FORBIDDEN.value());
                    body.put("message", "Acesso negado - Permissão insuficiente");
                    
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(response.getOutputStream(), body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            return null;
        };
    }
} 