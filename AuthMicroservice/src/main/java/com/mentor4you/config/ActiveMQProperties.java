package com.mentor4you.config;

import lombok.Builder;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "activemq")
@ConstructorBinding
@Value
@Builder
public class ActiveMQProperties {

    public static final String USER_EVENTS_TOPIC = "userEvents";

    String protocol;
    String host;
    String port;
}
