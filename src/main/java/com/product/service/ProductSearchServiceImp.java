package com.product.service;


import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.product.constant.Constants;
import com.product.document.PageSupport;
import com.product.document.Product;
import com.product.error.ProductNotFoundException;
import com.product.repository.CouchbaseRepository;
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
	@Autowired
	CouchbaseRepository repository;
	
	@Override
	public Mono<Product> findProductByKey(String key) {
		return productRepository.findById(key)
				.switchIfEmpty(Mono.error(new ProductNotFoundException(String.format(Constants.PRODUCT_NOT_EXIST_MEASAGE,key))))
	    		  .doOnError(e->logger.error(Constants.PRODUCT_ERROR_MEASAGE,e.getMessage()))
	    		   .doOnSuccess(p->logger.info("product key {} has been fetched successfully",p.getProductKey()));
	}

	

	@Override
	public Flux<Product> findProductByName(String productName,int page,int offset) {


		return productRepository.findByProductName(productName.toLowerCase(),page,getOffset(offset))
				.switchIfEmpty(Flux.error(new ProductNotFoundException(String.format(Constants.PRODUCT_NOT_EXIST_MEASAGE,productName))))
			     .doOnError(e->logger.error(Constants.PRODUCT_ERROR_MEASAGE,e.getMessage()));
	}

	@Override
	public Flux<Product> findByProductSize(String size,int page,int offset) {
		return productRepository.findBySize(size.toLowerCase(),page,getOffset(offset))
				      .switchIfEmpty(Flux.error(new ProductNotFoundException(String.format(Constants.PRODUCT_NOT_EXIST_MEASAGE,size))))
	                    .doOnError(e->logger.error(Constants.PRODUCT_ERROR_MEASAGE,size,e.getMessage()));
	}

	@Override
	public Flux<Product> findProducts(int page,int offset) {
		
		return  productRepository.findProducts( page, getOffset(offset))
				.switchIfEmpty(Flux.error(new ProductNotFoundException(Constants.NOT_FOUND_MEASAGE)))
    		     .doOnError(e->logger.error(Constants.PRODUCT_ERROR_MEASAGE,e.getMessage()));
                  /*.sort((p1,p2)-> Long.valueOf(p1.getProductKey()).compareTo(Long.valueOf(p2.getProductKey())))
                   .skip(getOffset(offset))
                    .take(page);*/ 
	}

	

	@Override
	public Flux<Product> findProductsByName(String name, String size,int page,int offset) {
		return productRepository.findByProductDetails(name.toLowerCase(), size.toLowerCase(),page,getOffset(offset))
				.switchIfEmpty(Flux.error(new ProductNotFoundException(String.format("Product size(%S) and name(%S)  exists in data base.",size,name))))
	              .doOnError(e->logger.error(Constants.PRODUCT_ERROR_MEASAGE,name,size,e.getMessage()));

	}

	
	private int getOffset(int offset) {
		return ((offset-1)*page) ;
	}



	@Override
	public Mono<PageSupport<Product>> getProductPage(PageRequest page) {
		return productRepository.findAll()
				.collectList()
		        .map(list -> new PageSupport<Product>(
		            list
		                .stream()
		                .skip(page.getPageNumber() * page.getPageSize())
		                .limit(page.getPageSize())
		                .collect(Collectors.toList()),
		            page.getPageNumber(), page.getPageSize(), list.size()));
     }

}
