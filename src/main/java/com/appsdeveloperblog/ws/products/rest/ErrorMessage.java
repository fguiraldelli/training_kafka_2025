package com.appsdeveloperblog.ws.products.rest;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ErrorMessage {

  private Date timestamp;
  private String message;
  private String details;

}
