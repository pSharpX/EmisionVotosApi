package edu.cibertec.votoelectronico.service.impl;

import java.util.List;

import javax.inject.Inject;

import edu.cibertec.votoelectronico.domain.GrupoPolitico;
import edu.cibertec.votoelectronico.repository.GrupoPoliticoRepository;
import edu.cibertec.votoelectronico.service.GrupoPoliticoService;

public class GrupoPoliticoServiceImpl implements GrupoPoliticoService {

	@Inject
	private GrupoPoliticoRepository repository;

	@Override
	public List<GrupoPolitico> list() {
		return this.repository.getAll();
	}

	@Override
	public GrupoPolitico findByName(String name) throws Exception {
		return this.repository.findByName(name);
	}

}
