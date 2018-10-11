package com.news.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Elastic6Application {

	public static void main(String[] args) {
		SpringApplication.run(Elastic6Application.class, args);
	}
	
	//TODO:
	// 1. Unique Tags for given keywords 
	// 2. Unique Sources for given keywords 
	// 3. Return news for given keywords 
	// 4. Return news for given keywords and Filters
	
	
}
