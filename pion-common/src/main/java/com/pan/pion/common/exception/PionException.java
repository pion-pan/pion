package com.pan.pion.common.exception;

/**
 * 自定义异常
 * 
 * @author
 *
 */
public class PionException extends RuntimeException {

	private static final long serialVersionUID = 9177431132177537679L;

	public PionException() {
		super();
	}

	public PionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PionException(String message, Throwable cause) {
		super(message, cause);
	}

	public PionException(String message) {
		super(message);
	}

	public PionException(Throwable cause) {
		super(cause);
	}
}
