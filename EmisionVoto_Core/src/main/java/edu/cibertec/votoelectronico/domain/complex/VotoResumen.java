package edu.cibertec.votoelectronico.domain.complex;

import java.util.List;

public class VotoResumen {

	private String grupoPolitico;
	private List<String> votantes;
	private int cantidad;

	public VotoResumen() {
		super();
	}

	public VotoResumen(String grupoPolitico, int cantidad) {
		super();
		this.grupoPolitico = grupoPolitico;
		this.cantidad = cantidad;
	}

	public VotoResumen(String grupoPolitico, List<String> votantes, int cantidad) {
		super();
		this.grupoPolitico = grupoPolitico;
		this.votantes = votantes;
		this.cantidad = cantidad;
	}

	public String getGrupoPolitico() {
		return grupoPolitico;
	}

	public void setGrupoPolitico(String grupoPolitico) {
		this.grupoPolitico = grupoPolitico;
	}

	public List<String> getVotantes() {
		return votantes;
	}

	public void setVotantes(List<String> votantes) {
		this.votantes = votantes;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
