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

    public static final String QUEUE_NAME = "defaultQueue";
    public static final String TOPIC_NAME = "defaultTopic";

    String protocol;
    String host;
    String port;
    String queue;
}
