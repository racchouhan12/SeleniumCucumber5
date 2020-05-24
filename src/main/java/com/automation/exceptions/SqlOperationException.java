package com.automation.exceptions;

public class SqlOperationException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String message;

	public SqlOperationException(final String message) {
		super(message);
		this.message = message;
	}

	public SqlOperationException(final Exception e) {
		super(e);
		this.message = e.getMessage();
	}

	public SqlOperationException(final String message, final Exception e) {
		super(message, e);
		this.message = e.getMessage();
	}

	@Override
	public String getMessage() {
		return message;
	}
}
