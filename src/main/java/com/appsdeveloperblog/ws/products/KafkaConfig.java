package com.appsdeveloperblog.ws.products;

import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

  @Value("${create.product.topic.name}")
  private String createProductTopicName;

  @SuppressWarnings("null")
  @Bean
  NewTopic createTopic() {
    return TopicBuilder.name(
        createProductTopicName)
        .partitions(3)
        .replicas(3)
        .configs(Map.of("min.insync.replicas", "2"))
        .build();
  }

}
