package com.agnyanam.yatrasevak.exception;

public class YathraSevakLoginException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	private String code;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public YathraSevakLoginException(String code , String message) {
		super();
		this.message = message;
		this.code = code;
	}

}
