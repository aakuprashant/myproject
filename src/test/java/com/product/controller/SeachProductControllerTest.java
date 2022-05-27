package com.product.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.product.document.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductSearchServiceImp;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = SeachProductController.class)
@Import(ProductSearchServiceImp.class)
class SeachProductControllerTest {

	@MockBean
	ProductRepository repository;
	 
    @Autowired
	private WebTestClient webClient;

	@Test
	void testFindByProductName() {
		Product product = new Product();
		product.setProductKey(1L);
		product.setProductName("Test");
		product.setSize("L");
		
        Mockito.when(repository.save(product)).thenReturn(Mono.just(product));
        
        Mockito.when(repository.findByProductName("Test",1,1)).thenReturn(Flux.just(product));
	    
	    webClient.get()
	      .uri("/api/v1/products/name/"+"test?page=10&offset=1")
	      .exchange()
	      .expectStatus().isOk();
	 
        Mockito.when(repository.findByProductName("test",1,1)).thenReturn(Flux.just(product));
	    
	    
	}
	
	@Test
	void testFindBySize() {
		Product product = new Product();
		product.setProductKey(1L);
		product.setProductName("Test");
		product.setSize("L");
		
        Mockito.when(repository.save(product)).thenReturn(Mono.just(product));
        Mockito.when(repository.findBySize("L",1,1)).thenReturn(Flux.just(product));

	    webClient.get()
	      .uri("/api/v1/products/size/"+"L?page=10&offset=1")
	      .exchange()
	      .expectStatus().isOk();
	 

	    
	    
	}
	
	@Test
	void testFindByProductDetails() {
		Product product = new Product();
		product.setProductKey(1L);
		product.setProductName("Test");
		product.setSize("L");
		
        Mockito.when(repository.save(product)).thenReturn(Mono.just(product));
        Mockito.when(repository.findByProductDetails("Test","L",1,1)).thenReturn(Flux.just(product));

	    webClient.get()
	      .uri("/api/v1/products/name/Test/size/L"+"?page=1&offset=1")
	      .exchange()
	      .expectStatus().isOk();
	 
	    
	    
	}
	
	@Test
	void testFindProducts() {
		Product product = new Product();
		product.setProductKey(1L);
		product.setProductName("Test");
		product.setSize("L");
		
        Mockito.when(repository.save(product)).thenReturn(Mono.just(product));
        Mockito.when(repository.findProducts(10, 1)).thenReturn(Flux.just(product));

	    webClient.get()
	      .uri("/api/v1/products/?page=10&offset=1")
	      .exchange()
	      .expectStatus().isOk();
	 
	    
	    
	}

}
