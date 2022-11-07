package com.mentor4you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CategoryMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategoryMicroserviceApplication.class, args);
    }
}
