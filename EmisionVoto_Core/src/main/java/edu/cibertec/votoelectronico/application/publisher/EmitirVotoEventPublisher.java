package edu.cibertec.votoelectronico.application.publisher;

import edu.cibertec.votoelectronico.application.event.EmitirVotoEvent;
import edu.cibertec.votoelectronico.domain.Voto;

public interface EmitirVotoEventPublisher extends GenericEventPublisher<Voto, EmitirVotoEvent> {

	@Override
	default EmitirVotoEvent createEvent(Voto payload) {
		return new EmitirVotoEvent(payload);
	}
}
