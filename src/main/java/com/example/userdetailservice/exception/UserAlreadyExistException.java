package com.example.userdetailservice.exception;

public class UserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -5597144047037050612L;

	public UserAlreadyExistException(String message) {
		super(message);
	}
}

