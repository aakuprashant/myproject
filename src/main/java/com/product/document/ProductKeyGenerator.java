package com.product.document;

import java.util.concurrent.atomic.AtomicLong;




public class ProductKeyGenerator {

	
	private static AtomicLong id=new AtomicLong(0L);



	
	public static long generateId() {
		
		return id.incrementAndGet();
	}

}
