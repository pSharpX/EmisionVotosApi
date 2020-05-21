package edu.cibertec.votoelectronico.resource.communication;

import java.util.Collection;

import edu.cibertec.votoelectronico.dto.VotoDto;

public class ListVotoResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<VotoDto> data;

	private ListVotoResponse(boolean success, String message, Collection<VotoDto> data) {
		super(success, message);
		this.setData(data);
	}

	public ListVotoResponse() {
		super();
	}

	public ListVotoResponse(Collection<VotoDto> data) {
		this(true, "", data);
	}

	public ListVotoResponse(String message) {
		this(false, message, null);
	}

	public Collection<VotoDto> getData() {
		return data;
	}

	public void setData(Collection<VotoDto> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ListVotoResponse [data=" + data + "]";
	}

}
