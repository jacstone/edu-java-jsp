package com.codeforanyone.edujavajsp.model;

/** 
 * When a (lolcat) user id value is given that doesn't exist in the database,
 * this exception is thrown.
 * 
 * @author jennybrown and jacstone
 *
 */
public class UserNotFoundException extends Exception {

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String message) {
		super(message);
	}

}
