package com.codeforanyone.edujavajsp.model;

@SuppressWarnings("serial")
public class RoleNotFoundException extends Exception{
	/**
	 * serial variable not needed as this will write to db instead of file 
	 */
	//private static final long serialVersionUID = 1L;
	public RoleNotFoundException() {
		super();
	}

	public RoleNotFoundException(String message) {
		super(message);
	}

}
