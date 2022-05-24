package com.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.product.document.Product;
import com.product.service.ProductService;

import reactor.core.publisher.Mono;

@RestController
public class UpdateProductController {
	Logger logger = LoggerFactory.getLogger(UpdateProductController.class);

	@Autowired
	ProductService productService;
	
	  @PutMapping(value="/{key}",consumes = MediaType.APPLICATION_JSON_VALUE)
	  @ResponseStatus(HttpStatus.OK)
	  public Mono<Product> update(@PathVariable("key") int key ,@RequestBody Product product) {
		  Mono<Product> productdetail=productService.updateProduct(product,product.getProductKey());
		logger.info("product {} has been updated ",product.getProductKey());
	    return productdetail;
	  }
}