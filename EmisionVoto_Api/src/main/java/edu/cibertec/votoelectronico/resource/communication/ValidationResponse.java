package edu.cibertec.votoelectronico.resource.communication;

import java.util.Map;

public class ValidationResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, String> validations;

	private ValidationResponse(boolean success, String message, Map<String, String> validations) {
		super(success, message);
		this.setValidations(validations);
	}

	public ValidationResponse() {
		super();
	}

	public ValidationResponse(Map<String, String> validations) {
		this(false, "", validations);
	}

	public ValidationResponse(String message) {
		this(false, message, null);
	}

	public Map<String, String> getValidations() {
		return validations;
	}

	public void setValidations(Map<String, String> validations) {
		this.validations = validations;
	}

	@Override
	public String toString() {
		return "ValidationResponse [validations=" + validations + ", isSuccess()=" + isSuccess() + ", getMessage()="
				+ getMessage() + "]";
	}

}
