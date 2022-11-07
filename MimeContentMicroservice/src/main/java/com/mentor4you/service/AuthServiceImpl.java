package com.mentor4you.service;

import com.mentor4you.domain.LoginRequest;
import com.mentor4you.domain.LoginResponse;
import com.mentor4you.domain.TokenRequest;
import com.mentor4you.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final WebClient authMicroserviceClient;
    private static final String CHECK_TOKEN_PATH = "/api/auth/check";
    private static final String LOGIN_PATH = "/api/auth/login";

    @Override
    public boolean isValidToken(String token) {
        final Boolean isValid =
                authMicroserviceClient
                        .post()
                        .uri(uriBuilder -> uriBuilder.path(CHECK_TOKEN_PATH).build())
                        .bodyValue(new TokenRequest(token))
                        .exchangeToMono(this::responseToBoolean)
                        .block();
        return isValid != null && isValid;
    }

    @Override
    public String login(String email, String password) {
        return authMicroserviceClient
                .post()
                .uri(uriBuilder -> uriBuilder.path(LOGIN_PATH).build())
                .bodyValue(new LoginRequest(email, password))
                .retrieve()
                .bodyToMono(LoginResponse.class)
                .map(LoginResponse::getToken)
                .block();
    }

    private Mono<Boolean> responseToBoolean(ClientResponse response) {
        return Mono.just(response.statusCode().compareTo(HttpStatus.OK) == 0);
    }
}
