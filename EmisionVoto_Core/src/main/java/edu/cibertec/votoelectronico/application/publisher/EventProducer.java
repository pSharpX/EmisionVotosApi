//package edu.cibertec.votoelectronico.application.publisher;
//
//import java.time.Instant;
//
//import javax.ejb.Schedule;
//import javax.ejb.Singleton;
//import javax.ejb.Startup;
//import javax.enterprise.event.Event;
//import javax.inject.Inject;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import edu.cibertec.votoelectronico.application.event.DomainEvent;
//
//@Startup
//@Singleton
//public class EventProducer {
//	private final Logger LOG = LoggerFactory.getLogger(EventProducer.class);
//
//	@Inject
//	Event<DomainEvent> domainEvents;
//
//	@Schedule(second = "*/10", minute = "*", hour = "*")
//	public void produceEvent() {
//		String domainText = "Hello, " + Instant.now();
//		LOG.info(domainText);
//		domainEvents.fire(new DomainEvent(domainText));
//	}
//}
