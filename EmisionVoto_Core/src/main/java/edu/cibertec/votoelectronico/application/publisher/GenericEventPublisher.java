package edu.cibertec.votoelectronico.application.publisher;

public interface GenericEventPublisher<P, E> {

	public E createEvent(P payload);

	public void publish(P payload);
}
