package com.mentor4you.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "credentials")
@ConstructorBinding
@Value
public class Credentials {

    String login;
    String password;
}
