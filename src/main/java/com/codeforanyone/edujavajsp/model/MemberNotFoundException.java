package com.codeforanyone.edujavajsp.model;

public class MemberNotFoundException extends Exception{

	/**
	 * serial variable not needed as this will write to db instead of file 
	 */
	//private static final long serialVersionUID = 1L;
	public MemberNotFoundException() {
		super();
	}

	public MemberNotFoundException(String message) {
		super(message);
	}


}
