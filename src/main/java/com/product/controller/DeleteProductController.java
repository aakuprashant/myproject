package com.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.product.service.ProductService;

import reactor.core.publisher.Mono;


@RestController
public class DeleteProductController {
	Logger logger = LoggerFactory.getLogger(DeleteProductController.class);
	@Autowired
	ProductService productService;
	
	  @DeleteMapping(value="/{key}")
	  @ResponseStatus(HttpStatus.OK)
	  public Mono<Void> delete(@PathVariable("key") Integer key) {
		  return productService.deleteProduct(key);
	  }

}
