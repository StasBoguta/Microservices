package com.mentor4you.service;

import com.mentor4you.domain.Category;
import com.mentor4you.domain.User;
import com.mentor4you.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtProvider jwtProvider;
    private final WebClient authMicroserviceClient;
    private static final String path = "/api/users/{id}";

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<User> getUserById(Integer id) {
        final User user =
                authMicroserviceClient
                        .get()
                        .uri(uriBuilder -> uriBuilder.path(path).build(id))
                        .headers(httpHeaders -> httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtProvider.getToken()))
                        .retrieve()
                        .bodyToMono(User.class)
                        .block();

        return CompletableFuture.completedFuture(user);
    }
}
