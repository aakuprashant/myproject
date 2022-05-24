package com.product.error.handler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.product.error.ProductExistsException;
import com.product.error.ProductNotFoundException;


@RestControllerAdvice
public class ProductServiceErrorHandler{
	 private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	 
	 @ExceptionHandler(ProductNotFoundException.class)
	 ResponseEntity<ErrorHandler> productNotFound(ProductNotFoundException ex) {
		 ErrorHandler errorHandler=new ErrorHandler(HttpStatus.NOT_FOUND);
		 errorHandler.setTimeStamp(dateFormat.format(timestamp));
		 errorHandler.setMessage(ex.getMessage());
		 ResponseEntity<ErrorHandler> entity=new ResponseEntity<ErrorHandler>(errorHandler,HttpStatus.NOT_FOUND);
	     return entity;

	  }
	 
	 @ExceptionHandler(ProductExistsException.class)
	 ResponseEntity<ErrorHandler> productExistException(ProductExistsException ex) {
		 ErrorHandler errorHandler=new ErrorHandler(HttpStatus.CONFLICT);
		 errorHandler.setTimeStamp(dateFormat.format(timestamp));
		 errorHandler.setMessage(ex.getMessage());
		 ResponseEntity<ErrorHandler> entity=new ResponseEntity<ErrorHandler>(errorHandler,HttpStatus.CONFLICT);
	     return entity;
      }
	 	@ExceptionHandler(Exception.class)
		 ResponseEntity<ErrorHandler> generalException(Exception ex) {
			 ErrorHandler errorHandler=new ErrorHandler(HttpStatus.BAD_REQUEST);
			 errorHandler.setTimeStamp(dateFormat.format(timestamp));
			 errorHandler.setMessage(ex.getMessage());
			 ResponseEntity<ErrorHandler> entity=new ResponseEntity<ErrorHandler>(errorHandler,HttpStatus.BAD_REQUEST);
		     return entity;
		 }
	 	@ResponseStatus(HttpStatus.BAD_REQUEST)
	 	@ExceptionHandler(MethodArgumentNotValidException.class)
	 	public Map<String, String> handleValidationExceptions(
	 	  MethodArgumentNotValidException ex) {
	 	    Map<String, String> errors = new HashMap<>();
	 	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	 	        String fieldName = ((FieldError) error).getField();
	 	        String errorMessage = error.getDefaultMessage();
	 	        errors.put(fieldName, errorMessage);
	 	    });
	 	    return errors;
	 	}
}
