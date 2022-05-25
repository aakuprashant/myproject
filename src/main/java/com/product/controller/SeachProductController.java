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
import org.springframework.web.bind.annotation.RestController;

import com.product.document.Product;
import com.product.service.ProductSearchService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SeachProductController {
	Logger logger = LoggerFactory.getLogger(SeachProductController.class);
	
	@Value("${product.defaultOffset}")
	private int offset;
	
	@Value("${product.page}")
	private int page;
	
	@Autowired
	ProductSearchService productSearchService;
	
	  @GetMapping("/{key}")
	  public ResponseEntity<Mono<Product>> findProductByKey(@PathVariable("key") Long key) {
	        Mono<Product> product =
	    		productSearchService.findProductByKey(key);
     	    return new ResponseEntity<Mono<Product>>(product, HttpStatus.OK);
	  }
	
	  @GetMapping(value="/name/{name}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findProductByName(@PathVariable("name") String name) {
		return productSearchService
			    .findProductByName(name,offset);
	  }
	  
	  @GetMapping(value="/name/{name}/{offset}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findProductByName(@PathVariable("name") String name,@PathVariable("offset") int offset) {
		  
		  return productSearchService
				     .findProductByName(name,offset);
	  }
	  
	  @GetMapping(value="/name/{name}/size/{size}/{offset}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findProductByNameAndSize(@PathVariable("name") String name,@PathVariable("size") String size,@PathVariable("offset") int offset) {
		 return   productSearchService.findProductsByName(name,size,offset);
	  }

	  @GetMapping(value="/name/{name}/size/{size}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findProductByNameSize(@PathVariable("name") String name,@PathVariable("size") String size) {
		 return  productSearchService
                  .findProductsByName(name,size,offset);
	  }
	  @GetMapping(value = "/size/{size}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findBySize(@PathVariable("size") String size) {
		   return  productSearchService
					.findByProductSize(size,offset);  
	  }
	  
	  @GetMapping(value = "/size/{size}/{offset}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findBySize(@PathVariable("size") String size,@PathVariable("offset") int offset) {

		        return productSearchService
					     .findByProductSize(size,offset);
	  }
	  @GetMapping(value="/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findProducts() {
		  return
	    		productSearchService
	    		  .findProducts(offset);

				    
	  } 
	  @GetMapping(value="/offset/{offset}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findProducts(@PathVariable("offset") int offset) {
		  return
	    		productSearchService
	    		  .findProducts(offset); 
		

	  } 
}
