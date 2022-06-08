package com.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.document.Product;
import com.product.service.ProductSearchService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")

public class SeachProductController {
	Logger logger = LoggerFactory.getLogger(SeachProductController.class);
	
	@Value("${product.defaultOffset}")
	private int offset;
	
	
	
	@Autowired
	ProductSearchService productSearchService;
	
	  @GetMapping("/products/{key}")
	  public ResponseEntity<Mono<Product>> findProductByKey(@PathVariable("key") String key) {
	        Mono<Product> product =
	    		productSearchService.findProductByKey(key);
     	    return new ResponseEntity<>(product, HttpStatus.OK);
	  }
	
	  @GetMapping(value="/products/name/{name}")
	  public Flux<Product> findProductByName(@PathVariable("name") String name,
			  @RequestParam(name = "page") int page,
		      @RequestParam(name = "offset") int offset
			  ) {
		return productSearchService
			    .findProductByName(name,page,offset);
	  }
	 
	  @GetMapping(value="/products/name/{name}/size/{size}")
	  public Flux<Product> findProductByNameSize(@PathVariable("name") String name,@PathVariable("size") String size,
			  @RequestParam(name = "page") int page,
		      @RequestParam(name = "offset") int offset) {
		 return  productSearchService
                  .findProductsByName(name,size,page,offset);
	  }
	  @GetMapping(value = "/products/size/{size}")
	  public Flux<Product> findBySize(@PathVariable("size") String size,
			  @RequestParam(name = "page") int page,
		      @RequestParam(name = "offset") int offset) {
		   return  productSearchService
					.findByProductSize(size,page,offset);  
	  }
	 
	 
	  @GetMapping(value="/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findProducts(@RequestParam(name = "page") int page,
		      @RequestParam(name = "offset") int offset) {
		  return
	    		productSearchService
	    		  .findProducts(page,offset); 
		

	  } 
	 
}
