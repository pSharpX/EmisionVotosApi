package edu.cibertec.votoelectronico.dto;

public class GrupoPoliticoDto {
	private String grupoPoliticoId;
	private String nombre;
	private String descripcion;

	public GrupoPoliticoDto() {
		super();
	}

	public GrupoPoliticoDto(String grupoPoliticoId, String nombre, String descripcion) {
		super();
		this.grupoPoliticoId = grupoPoliticoId;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getGrupoPoliticoId() {
		return grupoPoliticoId;
	}

	public void setGrupoPoliticoId(String grupoPoliticoId) {
		this.grupoPoliticoId = grupoPoliticoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
