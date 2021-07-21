package com.sapient.ofd.exception;

public class FoodDelivaryException extends Exception{
	private static final long serialVersionUID = 1L;	
	

	public FoodDelivaryException() {
		super("Unable to receive food order");
	}

	public FoodDelivaryException(String message) {
		super(message);	
	}


	public FoodDelivaryException(String message,Throwable e) {
		super(message,e);
	}

}
