package com.mentor4you.service;

import com.mentor4you.domain.TokenRequest;
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
    private static final String path = "/api/auth/check";

    @Override
    public boolean isValidToken(String token) {
        final Boolean isValid =
                authMicroserviceClient
                        .post()
                        .uri(uriBuilder -> uriBuilder.path(path).build())
                        .bodyValue(new TokenRequest(token))
                        .exchangeToMono(this::responseToBoolean)
                        .block();
        return isValid != null && isValid;
    }

    private Mono<Boolean> responseToBoolean(ClientResponse response) {
        return Mono.just(response.statusCode().compareTo(HttpStatus.OK) == 0);
    }
}
