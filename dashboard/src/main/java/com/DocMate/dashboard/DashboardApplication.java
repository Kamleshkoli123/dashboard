package com.DocMate.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.DocMate")
public class DashboardApplication {
	
    private static final Logger logger = LoggerFactory.getLogger(DashboardApplication.class);

	public static void main(String[] args) {
		logger.info("Starting dashboard application..");
		SpringApplication.run(DashboardApplication.class, args);
		logger.info("dashboard application Started..");
	}
}
