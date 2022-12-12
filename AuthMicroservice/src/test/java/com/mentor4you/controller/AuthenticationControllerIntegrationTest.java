package com.mentor4you.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Path;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerIntegrationTest {

    private GenericContainer userMicroserviceContainer = new GenericContainer(new ImageFromDockerfile()
            .withDockerfilePath("./../UserMicroservice/Dockerfile"));
    @Test
    public void containerStarts() {
    }
}
