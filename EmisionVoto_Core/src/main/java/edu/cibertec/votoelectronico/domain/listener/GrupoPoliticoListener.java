package edu.cibertec.votoelectronico.domain.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.domain.Voto;

public class GrupoPoliticoListener {
	private final Logger LOG = LoggerFactory.getLogger(GrupoPoliticoListener.class);

	@PrePersist
	public void beforePersist(Voto object) {
		LOG.info("BeforePersist EventListener");
	}

	@PreUpdate
	public void beforeUpdate(Voto object) {
		LOG.info("BeforeUpdate EventListener");
	}
}
