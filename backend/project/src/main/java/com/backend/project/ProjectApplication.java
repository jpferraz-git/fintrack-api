package com.backend.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.backend.project.infrastructure.entity")
public class ProjectApplication {

	public static void main(String[] args) {
		System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder(12).encode("admin"));

		SpringApplication.run(ProjectApplication.class, args);
	}

}
