package com.appsdeveloperblog.ws.products.rest;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatedProductRestModel {

  private String title;
  private BigDecimal price;
  private Integer quantity;

}
