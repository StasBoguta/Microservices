package com.mentor4you.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "activemq")
@ConstructorBinding
@Value
public class ActiveMQProperties {

  public static final String MENTOR_EVENTS_TOPIC = "mentorEventsTopic";

  String protocol;
  String host;
  String port;
}
