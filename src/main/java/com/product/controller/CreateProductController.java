package com.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.java.Bucket;
import com.product.document.Product;
import com.product.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CreateProductController {
	
	
	Logger logger = LoggerFactory.getLogger(CreateProductController.class);
	@Autowired
	ProductService productService;
	
	  
	  @PostMapping(value="/",consumes = MediaType.APPLICATION_JSON_VALUE)
	  @ResponseStatus(HttpStatus.CREATED)
	  public Mono<Product> createProduct(@RequestBody Product product) {
		  Mono<Product> productDetail=productService.createProduct(product);
		  logger.info("new product has been created "); 
	    return productDetail;
	   
	  }
	  
}
