package com.product.service;


import com.product.document.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
	public Mono<Product> createProduct(Product product);
	public Mono<Product> updateProduct(Product product,long key);
	public <T> Mono<Product> deleteProduct(long key);
	public Flux<Product> createProducts(Flux<Product> products);
}
