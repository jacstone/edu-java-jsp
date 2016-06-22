package com.codeforanyone.edujavajsp.model;

@SuppressWarnings("serial")
public class DataNotFoundException extends Exception{
	/**
	 * serial variable not needed as this will write to db instead of file 
	 */
	//private static final long serialVersionUID = 1L;
	public DataNotFoundException() {
		super();
	}

	public DataNotFoundException(String message) {
		super(message);
	}


}
