package com.product.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.document.GeneratorKey;
import com.product.document.Product;
import com.product.error.ProductNotFoundException;
import com.product.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImp implements ProductService {
	

	
	Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	@Autowired
	 ProductRepository productRepository;
	
	@Override
	public Mono<Product> createProduct(Product product) {
		if(product.getProductKey() ==0 )
		     product.setProductKey(GeneratorKey.generateId());
		return this.productRepository
	            .save(product)
	             .doOnError(th->logger.error("failed to create ,Error message : {}",th.getMessage()))
	              .doOnSuccess(p ->logger.info("product key {} has been created seccessfully",p.getProductKey()));

	}

	@Override
	public Mono<Product> updateProduct(Product product, long key) {
		Mono<Product> prod=productRepository
		.findById(key)
         .switchIfEmpty(Mono.error(new ProductNotFoundException(String.format("Product key ( %S ) does not exist. error",key))))
          .map(p -> new Product(p.getProductKey(), product.getProductName(),product.getSize()));
		
		this.productRepository.deleteById(key);
		
		return prod.flatMap(p -> this.productRepository.save(p))
				.doOnError(th->logger.error("failed to create ,Error message : {}",th.getMessage()))
	              .doOnSuccess(p ->logger.info("product key {} has been updated seccessfully",p.getProductKey()));
         
	}
	
	 

	
	public  <T> Mono<Product> deleteProduct(long key) {
		
		return
			productRepository
		       .findById(key)
		         .switchIfEmpty(Mono.error(new ProductNotFoundException(String.format("Product key ( %S ) does not exist. error",key))))
		           .flatMap(p -> this.productRepository.deleteById(p.getProductKey()).thenReturn(p))
		            .doOnError(th->logger.error("failed to delete ,Error message : {}",th.getMessage()))
		             .doOnSuccess(p->logger.info("product key {} has been deleted seccessfully",p.getProductKey()));
		        
	}

	@Override
	public  Flux<Product> createProducts(Flux<Product> products) {
		 products.flatMap(p->productRepository.save(p))
		  .onErrorContinue((th, p) ->logger.error("Exception: " + th.getMessage()))
           .subscribe( p -> logger.info("products {} have been created seccessfully",p.getProductKey()),
                        e -> logger.error("failed to create ,Error message : {}",e.getMessage()));
		
		return products;
		 
	}

	

	
}
