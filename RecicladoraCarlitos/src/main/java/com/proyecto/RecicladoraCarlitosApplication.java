package com.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.proyecto" })
public class RecicladoraCarlitosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecicladoraCarlitosApplication.class, args);
	}

}
