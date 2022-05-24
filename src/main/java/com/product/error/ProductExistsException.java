package com.product.error;

public class ProductExistsException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public ProductExistsException(String message) {
		super(message);
	}

	public String getMessage() {
		return super.getMessage();
	}
	
	

}
