package com.product.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
@WebFluxTest(CreateProductController.class)
@Import(ProductServiceImp.class)
public class CreateProductControllerTest {

	@MockBean
	ProductRepository productService;
	 
    @Autowired
	private WebTestClient webClient;
    
    
	@Test
	public void testUpdate() {
		Product product = new Product();
		product.setProductKey(1L);
		product.setProductName("Test");
		product.setSize("L");
	    Mono<Product> p=Mono.just(product);
	    Mockito.when(productService.save(product)).thenReturn(Mono.just(product));
	    

	    webClient.put()
        .uri("/1")
        .body(Mono.just(p), Product.class)
        .exchange()
        .expectStatus().isNotFound();
	    
	   
	    
	    
        /*.expectBody(Product.class)
        .value(productRes -> {
                    Assertions.assertThat(productRes.getProductKey()).isEqualTo(1);
                    Assertions.assertThat(productRes.getProductName()).isEqualTo("Test");
                    Assertions.assertThat(productRes.getSize()).isEqualTo("L");
                }
        );
	    Mockito.verify(repository, times(1)).save(product);
	   */
	}
	
	@Test
	public void testCreate() {
		Product product = new Product(1L,"Test","L");
		
	    Mono<Product> p=Mono.just(product);
	    Mockito.when(productService.save(product)).thenReturn(Mono.just(product));
	    
  
	    webClient.post()
        .uri("/")
        .body(Mono.just(p), Product.class)
        .exchange()
        .expectStatus()
        .isBadRequest();
	    
	    
	    
        /*.expectBody(Product.class)
        .value(productRes -> {
                    Assertions.assertThat(productRes.getProductKey()).isEqualTo(1);
                    Assertions.assertThat(productRes.getProductName()).isEqualTo("Test");
                    Assertions.assertThat(productRes.getSize()).isEqualTo("L");
                }
        );
	    Mockito.verify(repository, times(1)).save(product);
	   */
	}

}
