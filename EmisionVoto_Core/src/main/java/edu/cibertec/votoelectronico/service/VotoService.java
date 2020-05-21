package edu.cibertec.votoelectronico.service;

import java.util.List;
import java.util.Objects;

import edu.cibertec.votoelectronico.domain.Voto;
import edu.cibertec.votoelectronico.domain.complex.VotoResumen;

public interface VotoService extends CommonService<Voto> {

	default boolean exist(String dni) throws Exception {
		return Objects.nonNull(findByDni(dni));
	}

	Voto findByDni(String dni) throws Exception;

	void emitirVoto(Voto object) throws Exception;

	List<VotoResumen> results();

	List<VotoResumen> results(String grupoPolitico);

}
