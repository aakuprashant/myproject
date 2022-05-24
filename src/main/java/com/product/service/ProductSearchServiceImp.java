package com.product.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.product.document.Product;
import com.product.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;




@Service

public class ProductSearchServiceImp implements ProductSearchService {

	@Value("${product.page}")
	private int page;
	
	@Autowired
	ProductRepository productRepository;
	
	
	@Override
	public Mono<Product> findByProductKey(int key) {
		return productRepository.findById(key);
	}

	

	@Override
	public Flux<Product> findByProductName(String productName,int offset) {

		offset=(offset-1)*page ;

		return productRepository.findByProductName(productName,page,offset);
	}

	@Override
	public Flux<Product> findByProductSize(String size,int offset) {
		offset=(offset-1)*page;
		return productRepository.findBySize(size,page,offset);
	}

	@Override
	public Flux<Product> findProducts(int offset) {
		offset=(offset-1)*page ;
		return  productRepository.findAll();
	}



	@Override
	public Flux<Product> findByProductDetails(String productName, String size,int offset) {
		offset=(offset-1)*page ;
		return productRepository.findByProductDetails(productName, size,page,offset);
	}
	
	

}
