package com.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.product.document.Product;
import com.product.service.ProductService;

import reactor.core.publisher.Mono;


@RestController
public class DeleteProductController {
	Logger logger = LoggerFactory.getLogger(DeleteProductController.class);
	@Autowired
	ProductService productService;
	
	  @DeleteMapping(value="/products/{key}")
	  @ResponseStatus(HttpStatus.OK)
	  public Mono<Product> delete(@PathVariable("key") long key) {
		  logger.info("Start process  :deletion of new product"); 
		  return productService.deleteProduct(key);
	  }

}
