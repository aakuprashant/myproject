package com.product.service;

import com.product.document.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductSearchService {
	
    public Mono<Product> findProductByKey(long key);
    public Flux<Product> findProductByName(String productName,int offset);
    public Flux<Product> findByProductSize(String size,int offset);
    public Flux<Product> findProductsByName(String productName,String size,int offset);
    public Flux<Product> findProducts(int offset);
   
}
