package com.product.controller;



import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

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
	void testDeleteproduct() {
		Product product = new Product();
		product.setProductKey("1");
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
        .expectStatus().isOk()
        .expectBodyList(Product.class)
        .doesNotContain(product);
        
 
	}

}
