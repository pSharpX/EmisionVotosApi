package edu.cibertec.votoelectronico.resource.communication;

import java.util.Collection;

import edu.cibertec.votoelectronico.dto.VotoResumenDto;

public class ResumenProcesoResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<VotoResumenDto> data;

	private ResumenProcesoResponse(boolean success, String message, Collection<VotoResumenDto> data) {
		super(success, message);
		this.setData(data);
	}

	public ResumenProcesoResponse() {
		super();
	}

	public ResumenProcesoResponse(Collection<VotoResumenDto> data) {
		this(true, "", data);
	}

	public ResumenProcesoResponse(String message) {
		this(false, message, null);
	}

	public Collection<VotoResumenDto> getData() {
		return data;
	}

	public void setData(Collection<VotoResumenDto> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResumenProcesoResponse [data=" + data + "]";
	}

}
