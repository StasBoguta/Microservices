package com.mentor4you.service;

import com.mentor4you.domain.Category;
import com.mentor4you.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final JwtProvider jwtProvider;
    private final WebClient categoryMicroserviceClient;
    private static final String path = "/api/categories/{id}";

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<Category> getCategoryById(Integer id) {
        final Category category =
                categoryMicroserviceClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(path).build(id))
                .headers(httpHeaders -> httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtProvider.getToken()))
                .retrieve()
                .bodyToMono(Category.class)
                .block();

        return CompletableFuture.completedFuture(category);
    }
}
