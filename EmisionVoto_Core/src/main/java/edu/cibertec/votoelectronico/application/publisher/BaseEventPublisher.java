package edu.cibertec.votoelectronico.application.publisher;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

public abstract class BaseEventPublisher<T> {

	@Inject
	@Any
	protected Event<T> applicationEventPublisher;
}
