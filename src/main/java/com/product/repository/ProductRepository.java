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
public interface ProductRepository extends ReactiveCouchbaseRepository<Product, Integer> {

	  @Query("#{#n1ql.selectEntity} where #{#n1ql.filter} and productName = $1 ORDER BY productName LIMIT $2 OFFSET $3")
	  Flux<Product> findByProductName(final String productName,int page,int offset);
	  
	  @Query("#{#n1ql.selectEntity} where #{#n1ql.filter} and productName = $1 and size = $2 ORDER BY productName LIMIT $3 OFFSET $4 ")
	  Flux<Product> findByProductDetails(final String productName,final String Size,int limit,int offset);
	
      @Query("#{#n1ql.selectEntity} where #{#n1ql.filter} and size = $1 ORDER BY size LIMIT $2 OFFSET $3 ")
	  Flux<Product> findBySize(final String Size,int limit,int offset);
      
}
