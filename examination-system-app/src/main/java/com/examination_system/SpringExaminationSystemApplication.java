package com.examination_system;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.examination_system", // App config
		"com.examination_system.core", // Core beans
		"com.examination_system.auth", // Auth controllers & services
		"com.examination_system.exam" // Exam controllers & services
})
@EntityScan(basePackages = "com.examination_system.model.entity")
@EnableJpaRepositories(basePackages = "com.examination_system.repository")
public class SpringExaminationSystemApplication {
	public static void main(String[] args) {
		Logger logger = Logger.getAnonymousLogger();
		ConfigurableApplicationContext context = SpringApplication.run(SpringExaminationSystemApplication.class, args);
		logger.info("Application started successfully!");
		logger.info("Access H2 console at: http://localhost:8081/api/h2-console");

	}

}