package com.examination_system.app;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.examination_system")
@EntityScan(basePackages = "com.examination_system")
@EnableJpaRepositories(basePackages = "com.examination_system")
public class SpringExaminationSystemApplication {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(SpringExaminationSystemApplication.class.getName());
        SpringApplication.run(SpringExaminationSystemApplication.class, args);
        logger.info("Application started successfully!");
        logger.info("Access H2 console at: http://localhost:8081/api/h2-console");
        logger.info("Access Swagger UI at: http://localhost:8081/api/swagger-ui/index.html");
    }

}