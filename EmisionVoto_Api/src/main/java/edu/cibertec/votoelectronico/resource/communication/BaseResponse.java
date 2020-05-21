package edu.cibertec.votoelectronico.resource.communication;

import java.io.Serializable;

public class BaseResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean success;
	private String message;

	public BaseResponse() {
	}

	public BaseResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	protected void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	protected void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "BaseResponse [success=" + success + ", message=" + message + "]";
	}

}
