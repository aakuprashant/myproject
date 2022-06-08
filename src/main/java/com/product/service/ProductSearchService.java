package com.product.service;

import org.springframework.data.domain.PageRequest;

import com.product.document.PageSupport;
import com.product.document.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductSearchService {
	
    public Mono<Product> findProductByKey(String key);
    public Flux<Product> findProductByName(String productName,int page,int offset);
    public Flux<Product> findByProductSize(String size,int page,int offset);
    public Flux<Product> findProductsByName(String productName,String size,int page,int offset);
    public Flux<Product> findProducts(int page,int offset);
	public Mono<PageSupport<Product>> getProductPage(PageRequest of);

}
