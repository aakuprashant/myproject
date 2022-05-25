package com.product.repository;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.product.document.Product;

import reactor.core.publisher.Flux;

@SuppressWarnings("deprecation")
@Repository
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "product")
public interface ProductRepository extends ReactiveCouchbaseRepository<Product, Long> {

	  @Query("#{#n1ql.selectEntity} where lower(productName) = $1 order by productName LIMIT $2 OFFSET $3 ")
	  Flux<Product> findByProductName(final String productName,int page,int offset);
	  
	  @Query("#{#n1ql.selectEntity} where lower(productName) = $1 and lower(size) = $2  LIMIT $3 OFFSET $4 ")
	  Flux<Product> findByProductDetails(final String productName,final String Size,int limit,int offset);
	
      @Query("#{#n1ql.selectEntity} where lower(size) = $1 order by size LIMIT $2 OFFSET $3")
	  Flux<Product> findBySize(final String Size,int limit,int offset);
      
}
