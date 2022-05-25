package com.product.service;

import java.util.List;

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
	product.setProductKey(GeneratorKey.generateId());
		return this.productRepository
	            .save(product)
	             .doOnError(th->logger.error("failed to create ,Error message : {}",th.getMessage()))
	              .doOnSuccess(p ->logger.info("product key {} has been created seccessfully",p.getProductKey()));

	}

	@Override
	public Mono<Product> updateProduct(Product product, long key) {
		return productRepository
        .findById(key)
         .switchIfEmpty(Mono.error(new ProductNotFoundException(String.format("Product key ( %S ) does not exist. error",key))))
          .map(p -> new Product(p.getProductKey(), product.getProductName(),product.getSize()))
           .flatMap(this.productRepository::save)
             .doOnError(th->logger.error("failed to update ,Error message : {}",th.getMessage()))
             .doOnSuccess(p->logger.info("product key {} has been updated seccessfully",p.getProductKey()));
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
	public Flux<Product> createProducts(List<Product> products) {
		
		return productRepository.saveAll(products); 
	}

	

}
