package edu.cibertec.votoelectronico.dto;

public class VotoResumenDto {

	private String grupoPolitico;
	private int cantidad;

	public VotoResumenDto() {
		super();
	}

	public VotoResumenDto(String grupoPolitico, int cantidad) {
		super();
		this.grupoPolitico = grupoPolitico;
		this.cantidad = cantidad;
	}

	public String getGrupoPolitico() {
		return grupoPolitico;
	}

	public void setGrupoPolitico(String grupoPolitico) {
		this.grupoPolitico = grupoPolitico;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "VotoResumenDto [grupoPolitico=" + grupoPolitico + ", cantidad=" + cantidad + "]";
	}

}
