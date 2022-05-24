package com.product.error;

public class ProductNotFoundException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String message) {
		super(message);
	}

	public String getMessage() {
		return super.getMessage();
	}
	
	

}
