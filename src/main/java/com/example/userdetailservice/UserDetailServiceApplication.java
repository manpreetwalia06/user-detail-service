package com.example.userdetailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UserDetailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserDetailServiceApplication.class, args);
	}

}
