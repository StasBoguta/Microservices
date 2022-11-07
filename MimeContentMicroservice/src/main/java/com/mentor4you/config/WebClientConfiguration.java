package com.mentor4you.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    private final WebClientProperties webClientProperties;

    @Bean
    public WebClient authMicroserviceClient() {
        return WebClient
                .builder()
                .baseUrl(String.format("%s://%s:%s",
                    webClientProperties.getAuthMicroservice().getSchema(),
                    webClientProperties.getAuthMicroservice().getHost(),
                    webClientProperties.getAuthMicroservice().getPort()))
                .build();
    }

    @Bean
    public WebClient categoryMicroserviceClient() {
        return WebClient
                .builder()
                .baseUrl(String.format("%s://%s:%s",
                        webClientProperties.getCategoryMicroservice().getSchema(),
                        webClientProperties.getCategoryMicroservice().getHost(),
                        webClientProperties.getCategoryMicroservice().getPort()))
                .build();
    }
}
