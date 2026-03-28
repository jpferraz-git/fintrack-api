package com.backend.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = "com.backend.project.infrastructure.entity")
@EnableScheduling
public class ProjectApplication {

	public static void main(String[] args) { SpringApplication.run(ProjectApplication.class, args); }

}
