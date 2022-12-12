package com.mentor4you.controller;

import com.mentor4you.domain.DTO.jwt.JwtTokenResponse;
import com.mentor4you.domain.DTO.user.LoginUserDTO;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerIntegrationTest {

  @LocalServerPort private int ownPort;

  @Autowired private TestRestTemplate restTemplate;

  private static final GenericContainer<?> userMicroserviceContainer =
      new GenericContainer<>(
              new ImageFromDockerfile()
                  .withDockerfile(
                      Path.of(".", "..", "UserMicroservice", "Dockerfile")))
          .withExposedPorts(8080);

  @BeforeAll
  static void startContainer() {
    userMicroserviceContainer.start();
  }

  @AfterAll
  static void stopContainer() {
    userMicroserviceContainer.stop();
  }

  @DynamicPropertySource
  static void registerUserMicroservicesUrl(DynamicPropertyRegistry registry) {
    registry.add(
        "web.user-service.url",
        () ->
            String.format(
                "http://%s:%s",
                userMicroserviceContainer.getHost(),
                userMicroserviceContainer.getMappedPort(8080)));
  }

  @Test
  public void shouldLoginExistingUser() {
    ResponseEntity<JwtTokenResponse> tokenResponse =
        restTemplate.postForEntity(
            "http://localhost:%s%s".formatted(ownPort, "/api/auth/login"),
            new LoginUserDTO("admin@email.com", "123"),
            JwtTokenResponse.class);
    Assertions.assertEquals(tokenResponse.getStatusCode(), HttpStatus.OK);
    Assertions.assertNotNull(tokenResponse.getBody());
    Assertions.assertNotNull(tokenResponse.getBody().getAccessToken());
  }

  @Test
  public void shouldNotLoginExistingUserWithWrongPassword() {
    ResponseEntity<JwtTokenResponse> tokenResponse =
        restTemplate.postForEntity(
            "http://localhost:%s%s".formatted(ownPort, "/api/auth/login"),
            new LoginUserDTO("admin@email.com", "abc"),
            JwtTokenResponse.class);
    Assertions.assertEquals(tokenResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
  }

  @Test
  public void shouldNotLoginNotExistingUser() {
    ResponseEntity<JwtTokenResponse> tokenResponse =
        restTemplate.postForEntity(
            "http://localhost:%s%s".formatted(ownPort, "/api/auth/login"),
            new LoginUserDTO("random@email.com", "123"),
            JwtTokenResponse.class);
    Assertions.assertEquals(tokenResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
  }
}
