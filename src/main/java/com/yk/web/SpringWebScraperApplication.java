package com.yk.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringWebScraperApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringWebScraperApplication.class, args);
	}

}
