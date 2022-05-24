package com.product.controller;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.product.document.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductServiceImp;

import reactor.core.publisher.Mono;


@ExtendWith(SpringExtension.class)
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
		product.setProductKey(1);
		product.setProductName("Test");
		product.setSize("L");
		
        Mockito.when(repository.save(product)).thenReturn(Mono.just(product));
        Mono<Void> voidReturn  = Mono.empty();
 
        Mockito
        .when(repository.deleteById(1))
           .thenReturn(voidReturn);
	 
	 webClient.delete()
	    .uri("/{key}", 1)
         .exchange()
         .expectStatus().isNotFound();
        
 
	}

}
