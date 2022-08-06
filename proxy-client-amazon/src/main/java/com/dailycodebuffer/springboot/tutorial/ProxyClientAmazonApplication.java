package com.dailycodebuffer.springboot.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ProxyClientAmazonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyClientAmazonApplication.class, args);
	}

}
