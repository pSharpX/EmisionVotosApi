package edu.cibertec.votoelectronico.application.event;

import edu.cibertec.votoelectronico.domain.Voto;

public class EmitirVotoEvent implements PayloadEvent<Voto> {

	private Voto payload;

	public EmitirVotoEvent(Object source) {

	}

	public EmitirVotoEvent(Voto payload) {
		this.payload = payload;
	}

	@Override
	public Voto getPayload() {
		return this.payload;
	}

}
