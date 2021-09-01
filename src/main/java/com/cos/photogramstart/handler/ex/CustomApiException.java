package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{

	//jvm이 객체를 구분할 때 !
	private static final long serialVersionUID = 1L;
	
	public CustomApiException(String message) {
		super(message);
	}

}
