package com.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.document.Product;
import com.product.error.ProductExistsException;
import com.product.error.ProductNotFoundException;
import com.product.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	 ProductRepository productRepository;
	
	@Override
	public Mono<Product> createProduct(Product product) {
		return productRepository.existsById(product.getProductKey()).flatMap(isExist-> !isExist ?
                productRepository.save(product) :
                  Mono.error(new ProductExistsException(String.format("Product key ( %S ) exists in database",product.getProductKey()))));
	}

	@Override
	public Mono<Product> updateProduct(Product product, int key) {
		return productRepository.existsById(key).flatMap(isExist-> isExist ?
                productRepository.save(product) :
                	Mono.error(new ProductNotFoundException(String.format("Product key ( %S ) does not exist",key))) );
	}
	
	 

	
	public  <T> Mono<Void> deleteProduct(int key) {
		
		return productRepository.existsById(key).flatMap(isExist-> isExist ? 
				productRepository.deleteById(key) : 
					Mono.error(new ProductNotFoundException(String.format("Product key ( %S ) does not exist",key))) )   ;
	}

	@Override
	public Flux<Product> createProducts(List<Product> products) {
		return productRepository.saveAll(products);
	}

	

}
