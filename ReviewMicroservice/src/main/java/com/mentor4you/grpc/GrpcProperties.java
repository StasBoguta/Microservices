package com.mentor4you.grpc;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "grpc")
@ConstructorBinding
@Value
public class GrpcProperties {

    String serverUrl;
}
