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

@ExtendWith(SpringExtension.class)
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
	    
	    webClient.get()
	      .uri("/Products/productName/{productName}","test")
	      .exchange()
	      .expectStatus().isNotFound();
	 
        Mockito.when(repository.findByProductName("test",1,0)).thenReturn(Flux.just(product));
	    
	    
	}
	
	@Test
	void testFindBySize() {
		Product product = new Product();
		product.setProductKey(1L);
		product.setProductName("Test");
		product.setSize("L");
		
        Mockito.when(repository.save(product)).thenReturn(Mono.just(product));
	    
	    webClient.get()
	      .uri("/products/size/{size}","L")
	      .exchange()
	      .expectStatus().isNotFound();
	 
        Mockito.when(repository.findBySize("L",1,0)).thenReturn(Flux.just(product));
	    
	    
	}
	
	@Test
	void testFindByProductDetails() {
		Product product = new Product();
		product.setProductKey(1L);
		product.setProductName("Test");
		product.setSize("L");
		
        Mockito.when(repository.save(product)).thenReturn(Mono.just(product));
	    
	    webClient.get()
	      .uri("/Products/productName/{productName}/size/{size}","test","L")
	      .exchange()
	      .expectStatus().isNotFound();
	 
        Mockito.when(repository.findByProductDetails("test","L",1,0)).thenReturn(Flux.just(product));
	    
	    
	}
	
	@Test
	void testFindProducts() {
		Product product = new Product();
		product.setProductKey(1L);
		product.setProductName("Test");
		product.setSize("L");
		
        Mockito.when(repository.save(product)).thenReturn(Mono.just(product));
	    
	    webClient.get()
	      .uri("/Products/page/{offset}",1)
	      .exchange()
	      .expectStatus().isNotFound();
	 
        Mockito.when(repository.findAll()).thenReturn(Flux.just(product));
	    
	    
	}

}
