package com.product.service;

import java.util.List;

import com.product.document.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
	public Mono<Product> createProduct(Product product);
	//public Mono<Product> createProducts(Product product);
	public Mono<Product> updateProduct(Product product,int key);
	public <T> Mono<Void> deleteProduct(int key);
	public Flux<Product> createProducts(List<Product> products);
}
