package edu.cibertec.votoelectronico.resource;

import java.io.IOException;
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
import javax.enterprise.inject.Any;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.application.event.EmitirVotoEvent;
import edu.cibertec.votoelectronico.application.qualifier.VotoServiceQualifier;
import edu.cibertec.votoelectronico.domain.complex.VotoResumen;
import edu.cibertec.votoelectronico.dto.VotoResumenDto;
import edu.cibertec.votoelectronico.mapping.MapperFactoryRegistry;
import edu.cibertec.votoelectronico.resource.communication.BaseResponse;
import edu.cibertec.votoelectronico.resource.communication.ResumenProcesoResponse;
import edu.cibertec.votoelectronico.service.VotoService;

@Path("/votoelectronico/subscribe")
@Singleton
public class SimpleSSEVotoElectronicoResource implements SSEVotoElectronicoResource {

	private final Logger LOG = LoggerFactory.getLogger(SimpleSSEVotoElectronicoResource.class);

	@Context
	private Sse sse;

	@Inject
	private @VotoServiceQualifier VotoService service;
	@Inject
	private MapperFactoryRegistry mapper;

	private SseBroadcaster sseBroadcaster;
	private int lastEventId;
	private List<ResumenProcesoResponse> messages = new ArrayList<ResumenProcesoResponse>();

	@PostConstruct
	public void init() {
		LOG.info("On Init method...");
	}

	@Context
	public void setSse(Sse sse) {
		this.sse = sse;
		this.sseBroadcaster = this.sse.newBroadcaster();
		this.sseBroadcaster.onError((o, e) -> {
			LOG.error("Ocurred an error on broadcasting: ", e);
		});
		this.sseBroadcaster.onClose((eventSink) -> {
			LOG.info("Broadcast closed: ", eventSink);
		});
	}

	@GET
	@Lock(LockType.READ)
	@Path("/resultado")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@Override
	public void obtenerResultados(@HeaderParam(HttpHeaders.LAST_EVENT_ID_HEADER) @DefaultValue("-1") int lastEventId,
			@Context SseEventSink eventSink) throws IOException {
		if (lastEventId >= 0)
			this.replyLastMessage(lastEventId, eventSink);
		sseBroadcaster.register(eventSink);
		LOG.info("Client has being registered");
	}

	@Override
	public void onEmitirVotoEvent(@Observes @Any EmitirVotoEvent domainEvent) {
		LOG.info("EmitirVoto Event received");
		ResumenProcesoResponse response = this.fetchResumenProceso();
		this.messages.add(response);
		OutboundSseEvent event = createEvent(response, ++this.lastEventId);
		LOG.info("Server about to send Event");
		this.sseBroadcaster.broadcast(event);
	}

	private void replyLastMessage(int lastEventId, SseEventSink eventSink) {
		try {
			for (int i = lastEventId; i < messages.size(); i++) {
				eventSink.send(createEvent(messages.get(i), i + 1));
			}
		} catch (Exception e) {
			throw new InternalServerErrorException("Could not reply messages ", e);
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
		return this.sse.newEventBuilder().id(String.valueOf(id)).data(response)
				.mediaType(MediaType.APPLICATION_JSON_TYPE).build();
	}

	@Override
	public Response test() {
		try {
			return Response.status(Response.Status.OK).entity(new BaseResponse(true, "")).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new BaseResponse(false, e.toString()))
					.build();
		}
	}
}
