package com.mentor4you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AuthMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthMicroserviceApplication.class, args);
	}
}
