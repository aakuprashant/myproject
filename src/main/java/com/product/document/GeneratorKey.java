package com.product.document;

import java.util.Date;


public class GeneratorKey  {

	
	public static long generateId() {
		return new Date().getTime();
	}

}
