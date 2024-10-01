package com.ejercicio.recargas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RecargasApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecargasApplication.class, args);
	}

}
