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
	  public ResponseEntity<Mono<Product>> findByProductKey(@PathVariable("key") Integer key) {
	    Mono<Product> product = productSearchService.findByProductKey(key).switchIfEmpty(productSearchService.findByProductKey(0));
	    HttpStatus status = product != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
	    return new ResponseEntity<Mono<Product>>(product, status);
	  }
	
	  @GetMapping(value="/name/{name}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findByProductName(@PathVariable("name") String name) {
		Flux<Product> products= productSearchService.findByProductName(name,offset).switchIfEmpty(productSearchService.findByProductKey(0));

	    return products;
	  }
	  
	  @GetMapping(value="/name/{name}/{offset}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findByProductName(@PathVariable("name") String name,@PathVariable("offset") int offset) {
		  
		   Flux<Product> products= productSearchService.findByProductName(name,offset).switchIfEmpty(productSearchService.findByProductKey(0));
		  
	       return products;
	  }
	  
	  @GetMapping(value="/name/{name}/size/{size}/{offset}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findProductByNameSize(@PathVariable("name") String name,@PathVariable("size") String size,@PathVariable("offset") int offset) {
		  Flux<Product> products= productSearchService.findByProductDetails(name,size,offset).switchIfEmpty(productSearchService.findByProductKey(0));
		  logger.info("Total number of product {} which have product name  {} size {} ",name,size);
		  return  products;
	  }

	  @GetMapping(value="/name/{name}/size/{size}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findProductByNameSize(@PathVariable("name") String name,@PathVariable("size") String size) {
		  Flux<Product> products= productSearchService.findByProductDetails(name,size,offset).switchIfEmpty(productSearchService.findByProductKey(0));
		  logger.info("Total number of product {} which have product name  {} size {} ",name,size);
		  return  products;
	  }
	  @GetMapping(value = "/size/{size}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findBySize(@PathVariable("size") String size) {
		  Flux<Product> products= productSearchService.findByProductSize(size,offset).switchIfEmpty(productSearchService.findByProductKey(0));
		  //logger.info("Total number of product {} which have product size {} ",products.count().block().intValue(),size);
		  return  products;  
	  }
	  
	  @GetMapping(value = "/size/{size}/{offset}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findBySize(@PathVariable("size") String size,@PathVariable("offset") int offset) {
		  Flux<Product> products= productSearchService.findByProductSize(size,offset).switchIfEmpty(productSearchService.findByProductKey(0));
		  //logger.info("Total number of product {} which have product size {} ",products.count().block().intValue(),size);
		  return products;
	  }
	  @GetMapping(value="/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findAll() {
	    Flux<Product> products = productSearchService.findProducts(offset).switchIfEmpty(productSearchService.findByProductKey(0));
	    //logger.info("Total number of product {}  ",products.count().block().intValue());
	    
		  return  products.sort((p1,p2)-> Integer.valueOf(p1.getProductKey()).compareTo(Integer.valueOf(p2.getProductKey())))
				    .skip((offset-1)*page+1)
				    .take(page); 
	  } 
	  @GetMapping(value="/offset/{offset}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	  public Flux<Product> findAll(@PathVariable("offset") int offset) {
	    Flux<Product> products = productSearchService.findProducts(offset).switchIfEmpty(productSearchService.findByProductKey(0));
	    //logger.info("Total number of product {}  ",products.count().block().intValue());
		  return  products.sort((p1,p2)-> Integer.valueOf(p1.getProductKey()).compareTo(Integer.valueOf(p2.getProductKey())))
				    .skip((offset-1)*page+1)
				    .take(page);  
	  } 
}
