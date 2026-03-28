package com.biblioteca.erp_biblioteca;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ErpBibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpBibliotecaApplication.class, args);
	}

}

