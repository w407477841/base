package com.zyiot.commonservice.excepion;

public class DeleteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DeleteException() {
		super();
	}

	public DeleteException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeleteException(String message) {
		super(message);
	}

	public DeleteException(Throwable cause) {
		super(cause);
	}

}
