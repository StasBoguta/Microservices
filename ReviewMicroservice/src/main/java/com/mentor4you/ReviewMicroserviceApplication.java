package com.mentor4you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableDiscoveryClient
public class ReviewMicroserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReviewMicroserviceApplication.class, args);
  }
}
