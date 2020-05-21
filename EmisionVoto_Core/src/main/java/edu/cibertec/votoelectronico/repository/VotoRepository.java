package edu.cibertec.votoelectronico.repository;

import java.util.Date;
import java.util.List;

import edu.cibertec.votoelectronico.domain.Voto;
import edu.cibertec.votoelectronico.domain.complex.VotoResumen;

public interface VotoRepository extends CommonRepository<Voto, String> {

	Voto findByDni(String dni) throws Exception;

	List<VotoResumen> groupByGrupoPolitico();

	List<VotoResumen> groupByGrupoPolitico(String grupoPolitico);

	List<VotoResumen> groupByGrupoPolitico(String grupoPolitico, Date fecha);

	List<VotoResumen> groupByGrupoPolitico(Date fecha);

}
