package com.appsdeveloperblog.ws.products.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.appsdeveloperblog.ws.products.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<Object> createProduct(@RequestBody CreatedProductRestModel product) {
    try {
      String productId = productService.createProduct(product);
      return ResponseEntity.status(HttpStatus.CREATED).body(productId);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getMessage(), "/products");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
  }
}
