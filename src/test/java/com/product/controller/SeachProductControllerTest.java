package com.product.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.product.document.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductSearchService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(SeachProductController.class)
@AutoConfigureWebTestClient
class SeachProductControllerTest {

	@MockBean
	ProductRepository repository;
	
	@MockBean
	ProductSearchService searchService;
	 
    @Autowired
	private WebTestClient webClient;

	@Test
	void testFindByProductName() {
		List<Product> products=new ArrayList<Product>();

		Product product = new Product();
		product.setProductKey("1");
		product.setProductName("Test");
		product.setSize("L");
		products.add(product);
		
		product = new Product();
		product.setProductKey("2");
		product.setProductName("Test");
		product.setSize("XL");
		products.add(product);
		
        
        Mockito.when(searchService.findProducts(10,1)).thenReturn(Flux.fromIterable(products));
	    
	    webClient.get()
	      .uri("/api/v1/products"+"?page=10&offset=1")
	      .exchange()
	      .expectStatus().isOk()
	      .expectBodyList(Product.class)
	      .hasSize(2)
	      ;
	 
	    
	    
	}
	
	@Test
	void testFindBySize() {
		List<Product> products=new ArrayList<Product>();

		Product product = new Product();
		product.setProductKey("1");
		product.setProductName("Test");
		product.setSize("L");
		products.add(product);
		
		product = new Product();
		product.setProductKey("2");
		product.setProductName("Test");
		product.setSize("L");
		products.add(product);
		
        Mockito.when(searchService.findByProductSize("L",10,1)).thenReturn(Flux.fromIterable(products));

	    webClient.get()
	      .uri("/api/v1/products/size/"+"L?page=10&offset=1")
	      .exchange()
	      .expectStatus().isOk()
	      .expectBodyList(Product.class)
	      .hasSize(2);
	 
	   
	    
	    
	}
	
	@Test
	void testProductSize() {
		 List<Product> products=new ArrayList<Product>();

		 Product product = new Product();
		product.setProductKey("1");
		product.setProductName("Test");
		product.setSize("XL");
		products.add(product);
		
		Mockito.when(searchService.findByProductSize("XL",10,1)).thenReturn(Flux.fromIterable(products));

	    webClient.get()
	      .uri("/api/v1/products/size/L"+"?page=10&offset=1")
	      .exchange()
	      .expectStatus().isOk()
	      .expectBodyList(Product.class)
	      .hasSize(0);
	}
	
	@Test
	void testFindByProductDetails() {
		Product product = new Product();
		product.setProductKey("1");
		product.setProductName("Test");
		product.setSize("L");
		
        Mockito.when(searchService.findProductsByName("Test","L",1,1)).thenReturn(Flux.just(product));

	    webClient.get()
	      .uri("/api/v1/products/name/Test/size/L"+"?page=1&offset=1")
	      .exchange()
	      .expectStatus().isOk()
	      .expectBodyList(Product.class)
	      .hasSize(1)
	      .consumeWith(
	    		  productInfo->{
	    			  Product p=productInfo.getResponseBody().get(0);
	    			  assertEquals("Test", p.getProductName());
	    			  assertEquals("1", p.getProductKey());
	    			  assertEquals("L", p.getSize());

	    		  }
	            );
	    
	    
	}
	
	@Test
	void testFindByKey() {
		Product product = new Product();
		product.setProductKey("1");
		product.setProductName("Test");
		product.setSize("L");
		
        Mockito.when(searchService.findProductByKey("1")).thenReturn(Mono.just(product));

	    webClient.get()
	      .uri("/api/v1/products/1")
	      .exchange()
	      .expectStatus().isOk().expectBody(Product.class)
	      .consumeWith(
	    		  productInfo->{
	    			  Product p=productInfo.getResponseBody();
	    			  assertEquals("Test", p.getProductName());
	    			  assertEquals("1", p.getProductKey());
	    			  assertEquals("L", p.getSize());

	    		  }
	            );
	}
	
	@Test
	void testFindProducts() {
		Product product = new Product();
		product.setProductKey("1");
		product.setProductName("Test");
		product.setSize("L");
		
       Mockito.when(searchService.findProducts(10, 1)).thenReturn(Flux.just(product));

	    webClient.get()
	      .uri("/api/v1/products/?page=10&offset=1")
	      .exchange()
	      .expectStatus().isOk().expectStatus().isOk().expectBodyList(Product.class)
	      .consumeWith(
	    		  productInfo->{
	    			  Product p=productInfo.getResponseBody().get(0);
	    			  assertEquals("Test", p.getProductName());
	    			  assertEquals("1", p.getProductKey());
	    			  assertEquals("L", p.getSize());

	    		  }
	            );
	    
	    
	}

}
