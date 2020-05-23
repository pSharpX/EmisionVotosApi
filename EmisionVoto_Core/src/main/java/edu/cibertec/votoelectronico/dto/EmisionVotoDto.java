package edu.cibertec.votoelectronico.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.cibertec.votoelectronico.dto.validation.DniNotExist;
import edu.cibertec.votoelectronico.dto.validation.GrupoPoliticoMustExist;
import edu.cibertec.votoelectronico.dto.validation.ValidDate;

@NotNull
public class EmisionVotoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "GrupoPolitico cannot be null")
	@GrupoPoliticoMustExist(message = "Grupo Politico must exist")
	private String grupoPolitico;

	@NotBlank(message = "DNI cannot be null")
	@Size(min = 8, max = 8, message = "DNI must have 8 characters")
	@DniNotExist(message = "DNI already registered. It must be unique")
	private String dni;

	@NotNull(message = "Fecha cannot be null")
	@ValidDate(message = "Invalid date. Required format is {format}")
	private String fecha;

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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
