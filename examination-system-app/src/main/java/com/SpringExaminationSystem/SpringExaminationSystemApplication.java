package com.SpringExaminationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringExaminationSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringExaminationSystemApplication.class, args);
		System.out.println("Application started successfully!");
		System.out.println("Access H2 console at: http://localhost:8081/h2-console");

	}

}