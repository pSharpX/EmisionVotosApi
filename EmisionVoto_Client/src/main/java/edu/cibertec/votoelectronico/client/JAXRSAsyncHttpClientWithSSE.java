package edu.cibertec.votoelectronico.client;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
//import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.SseEventSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.client.base.CommonAsyncHttpClient;
import edu.cibertec.votoelectronico.client.base.HttpClient.REQUEST_METHOD;
import edu.cibertec.votoelectronico.client.qualifier.AsyncHttpClientQualifier;
import edu.cibertec.votoelectronico.client.base.HttpClientWithSSE;

@Dependent
public class JAXRSAsyncHttpClientWithSSE implements HttpClientWithSSE {

	private final Logger LOG = LoggerFactory.getLogger(JAXRSAsyncHttpClientWithSSE.class);

	@Inject
	@AsyncHttpClientQualifier
	private CommonAsyncHttpClient httpClient;

	@Override
	public <T> SseEventSource listenOn(String path, Map<String, Object> header, Consumer<T> consumer,
			Class<T> responseType) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(path);
		SseEventSource source = SseEventSource.target(target).build();
		source.register((inboundSseEvent) -> {
			try {
				final T data = inboundSseEvent.readData(responseType, MediaType.APPLICATION_JSON_TYPE);
				if (consumer != null)
					consumer.accept(data);
			} catch (Exception e) {
				LOG.error("Error ocurred in: [{}]", e);
			}
		});
		source.open();
		return source;
	}

	@Override
	public <T, E> CompletableFuture<T> requestAsync(String path, REQUEST_METHOD method, E entity,
			Map<String, Object> header, Class<T> responseType) {
		return this.httpClient.requestAsync(path, method, entity, header, responseType);
	}

}
