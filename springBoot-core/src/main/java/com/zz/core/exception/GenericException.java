package com.zz.core.exception;

public class GenericException extends Exception {
	private static final long serialVersionUID = 1L;
	public GenericException(){
		super();
	}
	public GenericException(String message){
		super(message);
	}
}
