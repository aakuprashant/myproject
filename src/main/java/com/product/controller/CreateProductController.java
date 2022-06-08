package com.product.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.product.document.Product;
import com.product.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class CreateProductController {
	
	
	Logger logger = LoggerFactory.getLogger(CreateProductController.class);
	@Autowired
	ProductService productService;
	
	  
	  @PostMapping(value="/product")
	  @ResponseStatus(HttpStatus.CREATED)
	  public Mono<Product> createProduct(@RequestBody Product product) {
	    return productService.createProduct(product);
	   
	  }
	  
	  @PostMapping(value="/products")
	  @ResponseStatus(HttpStatus.CREATED)
	  public Flux<Product> createProducts(@RequestBody List<Product> products) {
		  
	    return productService.createProducts(Flux.fromIterable(products));
	   
	  }
	  
	 
}
