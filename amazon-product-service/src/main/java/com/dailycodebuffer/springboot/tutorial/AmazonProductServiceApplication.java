package com.dailycodebuffer.springboot.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AmazonProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonProductServiceApplication.class, args);
	}

}
