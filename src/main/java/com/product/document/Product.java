package com.product.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdAttribute;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonAnyGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Product {
	
	@Id
	@GeneratedValue(strategy =GenerationStrategy.USE_ATTRIBUTES,delimiter = "")
	private String productKey;
	
    @IdAttribute
    private String idAtt=Long.toString(ProductKeyGenerator.generateId());
    
    @IdPrefix
    private String prefix=Long.toString(new Date().getTime());
		
	@Field
	private String productName;
	
	@Field
	private String size;

	
	
}
