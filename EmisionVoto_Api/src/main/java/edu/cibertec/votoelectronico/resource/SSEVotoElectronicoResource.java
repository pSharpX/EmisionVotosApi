package edu.cibertec.votoelectronico.resource;

import java.io.IOException;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSink;

import edu.cibertec.votoelectronico.application.event.EmitirVotoEvent;

@Path("/votoelectronico/subscribe")
public interface SSEVotoElectronicoResource {

	@GET
	@Lock(LockType.READ)
	@Path("/resultado")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void obtenerResultados(@HeaderParam(HttpHeaders.LAST_EVENT_ID_HEADER) @DefaultValue("-1") int lastEventId,
			@Context SseEventSink eventSink) throws IOException;

	@Lock(LockType.WRITE)
	public void onEmitirVotoEvent(@Observes @Any EmitirVotoEvent domainEvent);

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response test();

}
