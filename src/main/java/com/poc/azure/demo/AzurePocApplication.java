package com.poc.azure.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import service.MessagingController;

@SpringBootApplication
@ComponentScan(basePackageClasses = MessagingController.class)
public class AzurePocApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzurePocApplication.class, args);
	}

}
