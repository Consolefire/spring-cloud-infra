package com.consolefire.xpenser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TestApp01Application {

	public static void main(String[] args) {
		SpringApplication.run(TestApp01Application.class, args);
	}

}
