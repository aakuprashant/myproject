package com.product.controller;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.product.document.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductServiceImp;

import reactor.core.publisher.Mono;


@WebFluxTest(controllers = DeleteProductController.class)
@Import(ProductServiceImp.class)
class DeleteProductControllerTest {
	
	@MockBean
	ProductRepository repository;
	 
    @Autowired
	private WebTestClient webClient;
	
	@Test
	void test() {
		Product product = new Product();
		product.setProductKey(1L);
		product.setProductName("Test");
		product.setSize("L");
		
		 Mockito
         .when(this.repository.findById(product.getProductKey()))
         .thenReturn(Mono.just(product));
     Mockito
         .when(this.repository.deleteById(product.getProductKey()))
         .thenReturn(Mono.empty());
	 
	 webClient.delete()
	    .uri("/api/v1/products/"+product.getProductKey())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk();
        
 
	}

}
