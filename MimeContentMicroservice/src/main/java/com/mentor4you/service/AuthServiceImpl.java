package com.mentor4you.service;

import com.mentor4you.config.Credentials;
import com.mentor4you.domain.LoginRequest;
import com.mentor4you.domain.LoginResponse;
import com.mentor4you.domain.TokenRequest;
import com.mentor4you.domain.User;
import com.mentor4you.grpc.JwtTokenCheckRequest;
import com.mentor4you.grpc.JwtTokenCheckResponse;
import com.mentor4you.grpc.JwtTokenServiceGrpc;
import com.mentor4you.grpc.JwtTokenServiceGrpc.JwtTokenServiceBlockingStub;
import com.mentor4you.domain.proto.JwtToken;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final WebClient authMicroserviceClient;
    private static final String CHECK_TOKEN_PATH = "auth-microservice:9090";
    private static final String LOGIN_PATH = "http://auth-microservice:8080/api/auth/login/proto";

    private final JwtTokenServiceBlockingStub jwtTokenServiceBlockingStub;

    private final RestTemplate restTemplate;
    private final Credentials credentials;

    public AuthServiceImpl(WebClient webClient, RestTemplate restTemplate, Credentials credentials) {
        this.authMicroserviceClient = webClient;
        this.restTemplate = restTemplate;
        this.credentials = credentials;

        ManagedChannel channel = ManagedChannelBuilder.forTarget(CHECK_TOKEN_PATH)
                .usePlaintext()
                .build();
        this.jwtTokenServiceBlockingStub = JwtTokenServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public boolean isValidToken(String token) {
        log.info("Validating token: {}", token);
        final JwtTokenCheckResponse response =
                jwtTokenServiceBlockingStub.checkToken(JwtTokenCheckRequest.newBuilder().setToken(token).build());
        log.info("isValid response: {}", response.getIsValid());
        return response.getIsValid();
    }

    @Override
    public String login() {
        return restTemplate
                .postForEntity(LOGIN_PATH,
                        new LoginRequest(credentials.getLogin(), credentials.getPassword()),
                        JwtToken.JwtTokenProto.class)
                .getBody()
                .getToken();
    }

    private Mono<Boolean> responseToBoolean(ClientResponse response) {
        return Mono.just(response.statusCode().compareTo(HttpStatus.OK) == 0);
    }
}
