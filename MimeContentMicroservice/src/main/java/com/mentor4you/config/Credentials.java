package com.mentor4you.config;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "credentials")
@ConstructorBinding
@RequiredArgsConstructor
@Value
@Builder
public class Credentials {

    String email;
    String password;
}
