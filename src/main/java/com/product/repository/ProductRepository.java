package com.product.repository;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.product.document.Product;

import reactor.core.publisher.Flux;

@SuppressWarnings("deprecation")
@Repository
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "product")
public interface ProductRepository extends ReactiveCouchbaseRepository<Product, Long> {
	  @Query("#{#n1ql.selectEntity} where lower(productName) = $1 limit $2 offset $3 ")
	  Flux<Product> findByProductName(final String productName,int page,int offset);
	  
	  @Query("#{#n1ql.selectEntity} where lower(productName) = $1 and lower(size) = $2  limit $3 offset $4 ")
	  Flux<Product> findByProductDetails(final String productName,final String Size,int limit,int offset);
	
      @Query("#{#n1ql.selectEntity} where lower(size) = $1  limit $2 offset $3")
	  Flux<Product> findBySize(final String Size,int limit,int offset);
      
      @Query("#{#n1ql.selectEntity} order by meta().id limit $1 offset $2")
	  Flux<Product> findProducts(int limit,int offset);
      
      
      
}
