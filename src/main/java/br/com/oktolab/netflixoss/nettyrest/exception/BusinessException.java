package br.com.oktolab.netflixoss.nettyrest.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7448660762447357176L;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable exception) {
		super(message, exception);
	}

	public BusinessException(Throwable exception) {
		super(exception);
	}
	
	protected BusinessException(String message, Throwable exception, boolean enableSuppression, boolean writableStackTrace) {
		super(message, exception, enableSuppression, writableStackTrace);
	}

}
