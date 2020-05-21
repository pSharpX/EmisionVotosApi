package edu.cibertec.votoelectronico.service;

import java.util.Objects;

import edu.cibertec.votoelectronico.domain.GrupoPolitico;

public interface GrupoPoliticoService extends CommonService<GrupoPolitico> {

	default boolean exist(String name) throws Exception {
		return Objects.nonNull(findByName(name));
	}

	GrupoPolitico findByName(String name) throws Exception;
}
