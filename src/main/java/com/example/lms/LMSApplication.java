package com.example.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.example.lms", "com.example.lms.model"})

public class LMSApplication {
	public static void main(String[] args) {
		SpringApplication.run(LMSApplication.class, args);
	}
}
