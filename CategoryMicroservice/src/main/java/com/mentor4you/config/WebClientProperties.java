package com.mentor4you.config;

import lombok.Builder;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "web")
@ConstructorBinding
@Value
@Builder
public class WebClientProperties {

    Endpoint authMicroservice;

    @Value
    @Builder
    public static class Endpoint {
        String schema;
        String host;
        String port;
    }
}
