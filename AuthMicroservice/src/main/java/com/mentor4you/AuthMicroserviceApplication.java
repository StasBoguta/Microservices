package com.mentor4you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableDiscoveryClient
public class AuthMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthMicroserviceApplication.class, args);
	}
}
