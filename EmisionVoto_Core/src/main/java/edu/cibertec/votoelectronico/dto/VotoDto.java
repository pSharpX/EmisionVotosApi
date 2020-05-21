package edu.cibertec.votoelectronico.dto;

import java.util.Date;

public class VotoDto {

	private String votoId;
	private String grupoPolitico;
	private String dni;
	private Date fecha;

	public VotoDto() {
		super();
	}

	public VotoDto(String votoId, String grupoPolitico, String dni, Date fecha) {
		super();
		this.votoId = votoId;
		this.grupoPolitico = grupoPolitico;
		this.dni = dni;
		this.fecha = fecha;
	}

	public String getVotoId() {
		return votoId;
	}

	public void setVotoId(String votoId) {
		this.votoId = votoId;
	}

	public String getGrupoPolitico() {
		return grupoPolitico;
	}

	public void setGrupoPolitico(String grupoPolitico) {
		this.grupoPolitico = grupoPolitico;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "VotoDto [votoId=" + votoId + ", grupoPolitico=" + grupoPolitico + ", dni=" + dni + ", fecha=" + fecha
				+ "]";
	}

}
