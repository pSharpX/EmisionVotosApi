package edu.cibertec.votoelectronico.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.application.qualifier.VotoServiceQualifier;
import edu.cibertec.votoelectronico.domain.Voto;
import edu.cibertec.votoelectronico.domain.complex.VotoResumen;
import edu.cibertec.votoelectronico.dto.VotoResumenDto;
import edu.cibertec.votoelectronico.mapping.MapperFactoryRegistry;
import edu.cibertec.votoelectronico.resource.communication.ResumenProcesoResponse;
import edu.cibertec.votoelectronico.service.VotoService;

@Path("events-examples")
@Singleton
public class EventsResource {

	private static final Logger LOG = LoggerFactory.getLogger(EventsResource.class);

	@Context
	Sse sse;

	@Inject
	private @VotoServiceQualifier VotoService service;
	@Inject
	private MapperFactoryRegistry mapper;

	private SseBroadcaster sseBroadcaster;
	private int lastEventId;
	private List<ResumenProcesoResponse> messages = new ArrayList<>();

	@PostConstruct
	public void initSse() {
		sseBroadcaster = sse.newBroadcaster();
		sseBroadcaster.onError((o, e) -> {
		});
	}

	@GET
	@Lock(LockType.READ)
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void itemEvents(@HeaderParam(HttpHeaders.LAST_EVENT_ID_HEADER) @DefaultValue("-1") int lastEventId,
			@Context SseEventSink eventSink) {
		if (lastEventId >= 0)
			replayLastMessages(lastEventId, eventSink);
		sseBroadcaster.register(eventSink);
	}

	private void replayLastMessages(int lastEventId, SseEventSink eventSink) {
		try {
			for (int i = lastEventId; i < messages.size(); i++) {
				eventSink.send(createEvent(messages.get(i), i + 1));
			}
		} catch (Exception e) {
			throw new InternalServerErrorException("Could not replay messages ", e);
		}
	}

	private ResumenProcesoResponse fetchResumenProceso() {
		ResumenProcesoResponse response = null;
		try {
			LOG.info("Fetching proccess resume..");
			Collection<VotoResumen> resultados = this.service.results();
			Function<VotoResumen, VotoResumenDto> convert = (VotoResumen object) -> this.mapper.convertFrom(object,
					VotoResumenDto.class);
			Collection<VotoResumenDto> collection = resultados.stream().map(convert).collect(Collectors.toList());
			response = new ResumenProcesoResponse(collection);
		} catch (Exception e) {
			LOG.error("Ocurred an error while trying to get resume. " + e.getMessage());
			response = new ResumenProcesoResponse(e.getMessage());
		}
		return response;
	}

	private OutboundSseEvent createEvent(ResumenProcesoResponse response, int id) {
		return sse.newEventBuilder().id(String.valueOf(id)).data(response)
				.mediaType(MediaType.APPLICATION_JSON_PATCH_JSON_TYPE).build();
	}

	@Lock(LockType.WRITE)
	public void onEvent(@Observes Voto domainEvent) {
		String object = domainEvent.toString();
		LOG.info(object);
		LOG.info("EmitirVoto Event received");
		ResumenProcesoResponse response = this.fetchResumenProceso();
		LOG.info(response.toString());
		messages.add(response);
		OutboundSseEvent event = createEvent(response, ++lastEventId);
		sseBroadcaster.broadcast(event);
	}
}
