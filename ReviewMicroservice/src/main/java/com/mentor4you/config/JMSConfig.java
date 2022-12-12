package com.mentor4you.config;

import javax.jms.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
@RequiredArgsConstructor
public class JMSConfig {

  private final ActiveMQProperties activeMQProperties;

  @Bean
  public ConnectionFactory connectionFactory() {
    return new ActiveMQConnectionFactory(
        String.format(
            "%s://%s:%s",
            activeMQProperties.getProtocol(),
            activeMQProperties.getHost(),
            activeMQProperties.getPort()));
  }

  @Bean
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

  @Bean
  public JmsTemplate mentorJmsTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate();
    jmsTemplate.setConnectionFactory(connectionFactory());
    jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
    return jmsTemplate;
  }

  @Bean
  public DefaultJmsListenerContainerFactory topicJmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setPubSubDomain(true);
    return factory;
  }
}
