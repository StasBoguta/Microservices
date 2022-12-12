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

  public static final String MENTOR_EVENTS_QUEUE = "mentorEventsQueue";
  public static final String MENTOR_EVENTS_TOPIC = "mentorEventsTopic";
  public static final String MENTEE_EVENTS_TOPIC = "menteeEventsTopic";

  String protocol;
  String host;
  String port;
}
