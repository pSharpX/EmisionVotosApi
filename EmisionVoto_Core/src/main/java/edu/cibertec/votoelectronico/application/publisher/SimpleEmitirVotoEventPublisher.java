package edu.cibertec.votoelectronico.application.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.application.event.EmitirVotoEvent;
import edu.cibertec.votoelectronico.domain.Voto;

public class SimpleEmitirVotoEventPublisher extends BaseEventPublisher<EmitirVotoEvent>
		implements EmitirVotoEventPublisher {

	private final Logger LOG = LoggerFactory.getLogger(SimpleEmitirVotoEventPublisher.class);

	@Override
	public void publish(Voto payload) {
		LOG.info("EmitirVoto Event will be published...");
		this.applicationEventPublisher.fire(this.createEvent(payload));
		LOG.info("EmitirVoto Event was published...");
	}

}
