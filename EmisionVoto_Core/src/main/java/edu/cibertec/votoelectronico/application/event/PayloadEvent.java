package edu.cibertec.votoelectronico.application.event;

public interface PayloadEvent<T> {

	public T getPayload();

}
