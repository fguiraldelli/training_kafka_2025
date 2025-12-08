package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.products.rest.CreatedProductRestModel;

public interface ProductService {

  String createProduct(CreatedProductRestModel createdProductRestModel) throws Exception;

}
