package com.zyiot.commonservice.excepion;

public class AddException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddException() {
		super();
	}

	public AddException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AddException(String message, Throwable cause) {
		super(message, cause);
	}

	public AddException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AddException(Throwable cause) {
		super(cause);
	}
	
	

}
