package com.product.document;

import java.util.concurrent.atomic.AtomicInteger;


public class GeneratorKey  {

	private static AtomicInteger key=new AtomicInteger(1);
	
	public static int generateId() {
		return key.addAndGet(1);
	}

}
