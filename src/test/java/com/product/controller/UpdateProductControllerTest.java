package com.product.controller;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;


import com.product.document.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UpdateProductController.class)
@AutoConfigureWebTestClient
class UpdateProductControllerTest {

	@MockBean
	ProductRepository repository;
	
	@MockBean
	ProductService service;

    @Autowired
	private WebTestClient webClient;
    
  

	

	@Test
    void testUpdate() {
		Product product = new Product();
		product.setProductName("Product1");
		product.setSize("XL");
	    Mockito.when(service.createProduct(product)).thenReturn(Mono.just(product));
	   
	    webClient.put()
        .uri("/api/v1/products/1")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(product)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Product.class);
	    
       
	}

}
