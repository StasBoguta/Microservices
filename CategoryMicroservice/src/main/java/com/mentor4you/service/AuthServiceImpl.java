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

    private final AuthServiceFeignClient authServiceFeignClient;

    @Override
    public boolean isValidToken(String token) {
        return authServiceFeignClient
                .checkToken(new TokenRequest(token))
                .getStatusCode()
                .equals(HttpStatus.OK);
    }
}
