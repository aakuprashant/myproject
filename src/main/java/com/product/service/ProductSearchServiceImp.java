package com.product.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.product.document.Product;
import com.product.error.ProductNotFoundException;
import com.product.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;




@Service

public class ProductSearchServiceImp implements ProductSearchService {

	Logger logger = LoggerFactory.getLogger(ProductSearchServiceImp.class);
	
	@Value("${product.page}")
	private int page;
	

	@Autowired
	ProductRepository productRepository;
	
	
	@Override
	public Mono<Product> findProductByKey(long key) {
		return productRepository.findById(key)
				.switchIfEmpty(Mono.error(new ProductNotFoundException(String.format("Product key ( %S ) does not exist in data base.",key))))
	    		  .doOnError(e->logger.error("product key {} not able to search, error :{}",e.getMessage()))
	    		   .doOnSuccess(p->logger.info("product key {} has been fetched successfully",p.getProductKey()));
	}

	

	@Override
	public Flux<Product> findProductByName(String productName,int offset) {


		return productRepository.findByProductName(productName.toLowerCase(),page,offset)
				.switchIfEmpty(Flux.error(new ProductNotFoundException(String.format("Product name ( %S ) does not exist in data base.",productName))))
			     .doOnError(e->logger.error("product name {} not able to search.Error :{}",e.getMessage()));
	}

	@Override
	public Flux<Product> findByProductSize(String size,int offset) {
		return productRepository.findBySize(size.toLowerCase(),page,offset)
				      .switchIfEmpty(Flux.error(new ProductNotFoundException(String.format("Product size ( %S ) does not exist in data base.",size))))
	                    .doOnError(e->logger.error("product size {} not able to fetch, error :{}",size,e.getMessage()));
	}

	@Override
	public Flux<Product> findProducts(int offset) {
		int skipSize=(offset-1)*page+1 ;
		return  productRepository.findAll()
				.switchIfEmpty(Flux.error(new ProductNotFoundException(String.format("None of Product  exists in data base."))))
    		     .doOnError(e->logger.error("products are not able to fetch, error :{}",e.getMessage()))
                  .sort((p1,p2)-> Long.valueOf(p1.getProductKey()).compareTo(Long.valueOf(p2.getProductKey())))
                   .skip(skipSize)
                    .take(page); 
	}



	@Override
	public Flux<Product> findProductsByName(String name, String size,int offset) {
		return productRepository.findByProductDetails(name.toLowerCase(), size.toLowerCase(),page,offset)
				.switchIfEmpty(Flux.error(new ProductNotFoundException(String.format("Product size(%S) and name(%S)  exists in data base.",size,name))))
	              .doOnError(e->logger.error("product name {} and size {} not able to search, error :{}",name,size,e.getMessage()));

	}
	
	

}
