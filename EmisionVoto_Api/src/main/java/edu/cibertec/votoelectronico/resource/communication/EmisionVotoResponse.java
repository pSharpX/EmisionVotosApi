package edu.cibertec.votoelectronico.resource.communication;

import edu.cibertec.votoelectronico.dto.VotoDto;

public class EmisionVotoResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VotoDto data;

	private EmisionVotoResponse(boolean success, String message, VotoDto data) {
		super(success, message);
		this.setData(data);
	}

	public EmisionVotoResponse() {
		super();
	}

	public EmisionVotoResponse(VotoDto data) {
		this(true, "", data);
	}

	public EmisionVotoResponse(String message) {
		this(false, message, null);
	}

	public VotoDto getData() {
		return data;
	}

	public void setData(VotoDto data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "EmisionVotoResponse [data=" + data + ", toString()=" + super.toString() + "]";
	}

}
