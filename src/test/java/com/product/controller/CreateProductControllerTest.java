package com.product.controller;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


import com.product.document.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CreateProductController.class)
@AutoConfigureWebTestClient
 class CreateProductControllerTest {

	@MockBean
	ProductRepository repository;
	
	@MockBean
	ProductService service;

    @Autowired
	private WebTestClient webClient;
    
   
	
	@Test
	void testCreateSingleProduct() {
		Product product = new Product();
		product.setProductName("test");
		product.setSize("L");

		
		
	    Mockito.when(service.createProduct(product)).thenReturn(Mono.just(product));
	   
	    webClient.post()
        .uri("/api/v1/product")
        .bodyValue(product)
        .exchange()
        .expectStatus().isCreated()
        .expectBody(Product.class);
	    
       
	}
	
	@Test
	 void testCreateProducts() {
		List<Product> products=new ArrayList<Product>();
		Product product = new Product();
		product.setProductKey("1");
		product.setProductName("product");
		product.setSize("L");
        products.add(product);
		product = new Product();
		product.setProductKey("2");
		product.setProductName("product1");
		product.setSize("L");
        products.add(product);

	    Mockito.when(service.createProducts(Flux.fromIterable(products))).thenReturn(Flux.fromIterable(products));
	   
	    webClient.post()
        .uri("/api/v1/products")
        .bodyValue(products)
        .exchange()
        .expectStatus().isCreated()
        .expectBodyList(Product.class)
       /* .hasSize(2)
	    .consumeWith(
	    	prodinfo->{
	    		Product prod=prodinfo.getResponseBody().get(0);
	    		assertNotNull(prod.getProductKey());
	    		System.out.println(prod.getProductKey());
	    	})*/
	    ;
       
	}
	

}
