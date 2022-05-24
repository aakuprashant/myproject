package com.product;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.document.Product;
import com.product.repository.ProductRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication

public class ProductServiceApplication 
{
	@Autowired
	ProductRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	
	
   
}
