package com.product.service;

import com.product.document.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductSearchService {
	
    public Mono<Product> findByProductKey(int key);
    public Flux<Product> findByProductName(String productName,int offset);
    public Flux<Product> findByProductSize(String size,int offset);
    public Flux<Product> findByProductDetails(String productName,String size,int offset);
    public Flux<Product> findProducts(int offset);
   
}
