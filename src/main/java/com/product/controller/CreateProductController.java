package com.product.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.product.document.Product;
import com.product.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CreateProductController {
	
	
	Logger logger = LoggerFactory.getLogger(CreateProductController.class);
	@Autowired
	ProductService productService;
	
	  
	  @PostMapping(value="/product",consumes = MediaType.APPLICATION_JSON_VALUE)
	  @ResponseStatus(HttpStatus.CREATED)
	  public Mono<Product> createProduct(@RequestBody Product product) {
		  Mono<Product> productDetail=productService.createProduct(product);
	    return productDetail;
	   
	  }
	  
	  @PostMapping(value="/products",consumes = MediaType.APPLICATION_JSON_VALUE)
	  @ResponseStatus(HttpStatus.CREATED)
	  public Flux<Product> createProducts(@RequestBody List<Product> products) {
		  
		  Flux<Product> productDetail=productService.createProducts(Flux.fromIterable(products));
	    return productDetail;
	   
	  }
	  
	 
}
