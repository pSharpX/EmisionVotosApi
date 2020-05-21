package edu.cibertec.votoelectronico.application.publisher;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.domain.Voto;

public class EmitirVotoEventProducer {
	private final Logger LOG = LoggerFactory.getLogger(EmitirVotoEventProducer.class);

	@Inject
	Event<Voto> domainEvents;

	public void produceEvent(Voto object) {
		LOG.info(object.toString());
		domainEvents.fire(object);
	}
}
