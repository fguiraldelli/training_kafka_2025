package com.appsdeveloperblog.ws.products.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import com.appsdeveloperblog.ws.products.rest.CreatedProductRestModel;

@Service
public class ProductServiceImpl implements ProductService {

  @Value("${create.product.topic.name}")
  private String createProductTopicName;

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

  public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }



  @SuppressWarnings("null")
  @Override
  public String createProduct(CreatedProductRestModel productRestModel){

    String productId = UUID.randomUUID().toString();

    // TODO: Persist Product Details into database table before publishing an Event.

    ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
        productId,
        productRestModel.getTitle(),
        productRestModel.getPrice(),
        productRestModel.getQuantity());

    /*
     ***************** This code is to work synchronous ***********************
     */

    //  SendResult<String, ProductCreatedEvent> result =
    //     kafkaTemplate.send(createProductTopicName, productId, productCreatedEvent).get();



    /*
      ***************** This code is to work asynchronous ***********************
    */

    CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
        kafkaTemplate.send(createProductTopicName, productId, productCreatedEvent);

    future.whenComplete((result, exception) -> {

      if (exception != null) {
        LOGGER.error("************ Failed to send message: " + exception.getMessage());
      } else {
        LOGGER.info("************ Message sent successfully: " + result.getRecordMetadata());
      }

    });

    /*
     * if I want to wait for the confirmation that is stored on Kafka Cluster (it means be
     * syncronous) It will block the thread until have the Kafka confirmation Althought the line
     * below works it is not recommend due can cause a misunderstand to other developers because
     * they can think that this code is asynchronous because there is CompletableFuture in the code
     * above. So to to this class synchronous, we have to delete the CompletableFuture.
     */
    // future.join();

    LOGGER.info("************ Returning product id");

    return productId;
  }

}
