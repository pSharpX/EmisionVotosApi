package edu.cibertec.votoelectronico.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.application.publisher.EmitirVotoEventProducer;
import edu.cibertec.votoelectronico.application.publisher.EmitirVotoEventPublisher;
import edu.cibertec.votoelectronico.application.qualifier.VotoServiceQualifier;
import edu.cibertec.votoelectronico.domain.Voto;
import edu.cibertec.votoelectronico.domain.complex.VotoResumen;
import edu.cibertec.votoelectronico.repository.VotoRepository;
import edu.cibertec.votoelectronico.service.VotoService;

@VotoServiceQualifier
public class VotoServiceImpl implements VotoService {

	private final Logger LOG = LoggerFactory.getLogger(VotoServiceImpl.class);

	@Inject
	private VotoRepository repository;

	@Inject
	private EmitirVotoEventProducer eventProducer;

	@Inject
	private EmitirVotoEventPublisher eventPublisher;

	@Override
	public void emitirVoto(Voto object) throws Exception {
		try {
			this.repository.create(object);
			this.eventPublisher.publish(object);
			this.eventProducer.produceEvent(object);
		} catch (Exception e) {
			LOG.error("Ocurred an error while trying emit a vote. " + e.getMessage());
			throw e;
		}

	}

	@Override
	public List<Voto> list() {
		return this.repository.getAll();
	}

	@Override
	public List<VotoResumen> results() {
		return this.repository.groupByGrupoPolitico();
	}

	@Override
	public List<VotoResumen> results(String grupoPolitico) {
		return this.repository.groupByGrupoPolitico(grupoPolitico);
	}

	@Override
	public Voto findByDni(String dni) throws Exception {
		return this.repository.findByDni(dni);
	}

}
