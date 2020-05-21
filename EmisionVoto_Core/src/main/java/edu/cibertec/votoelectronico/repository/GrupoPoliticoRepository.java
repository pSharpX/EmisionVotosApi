package edu.cibertec.votoelectronico.repository;

import edu.cibertec.votoelectronico.domain.GrupoPolitico;

public interface GrupoPoliticoRepository extends CommonRepository<GrupoPolitico, String> {

	GrupoPolitico findByName(String name) throws Exception;

}
