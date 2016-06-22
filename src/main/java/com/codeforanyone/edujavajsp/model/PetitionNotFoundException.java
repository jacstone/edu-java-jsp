package com.codeforanyone.edujavajsp.model;

public class PetitionNotFoundException extends Exception {

	/**
	 * serial variable not needed as this will write to db instead of file 
	 */
	//private static final long serialVersionUID = 1L;
	public PetitionNotFoundException() {
		super();
	}

	public PetitionNotFoundException(String message) {
		super(message);
	}

}
