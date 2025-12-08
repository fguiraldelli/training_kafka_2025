package com.appsdeveloperblog.ws.products.service;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import com.appsdeveloperblog.ws.products.rest.CreatedProductRestModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

  @Value("${create.product.topic.name}")
  private String createProductTopicName;

  KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

  public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @SuppressWarnings("null")
  @Override
  public String createProduct(CreatedProductRestModel productRestModel) throws Exception {

    String productId = UUID.randomUUID().toString();

    // TODO: Persist Product Details into database table before publishing an Event.

    ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
        productId,
        productRestModel.getTitle(),
        productRestModel.getPrice(),
        productRestModel.getQuantity());

     log.info("************ Before publishing a ProductCreatedEvent");

     SendResult<String, ProductCreatedEvent> result =
        kafkaTemplate.send(createProductTopicName, productId, productCreatedEvent).get();

    //Metadata from Kafka broker
    log.info("************ Partition: " + result.getRecordMetadata().partition());
    log.info("************ Topic: " + result.getRecordMetadata().topic());
    log.info("************ Offset: " + result.getRecordMetadata().offset());
    log.info("************ Timestamp: " + result.getRecordMetadata().timestamp());
    
    //Log message
    log.info("************ Returning product id");

    return productId;
  }

}
