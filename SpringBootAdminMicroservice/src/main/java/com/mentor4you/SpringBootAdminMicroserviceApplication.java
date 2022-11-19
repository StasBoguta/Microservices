package com.mentor4you;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminMicroserviceApplication.class, args);
    }
}
