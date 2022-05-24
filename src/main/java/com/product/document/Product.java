package com.product.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;




@Document
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationStrategy.UNIQUE)
	private int productKey;
	
	@Field
	private String productName;
	
	
	@Field
	private String size;
	
	
	public int getProductKey() {
		return productKey;
	}
	public void setProductKey(int productKey) {
		
		this.productKey = productKey;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	
	

}
