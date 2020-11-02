package com.example.userdetailservice.exception;

public class UserNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 750766596210672499L;

	public UserNotFoundException(String message)   
	{  
		super(message);  
	}  
}
