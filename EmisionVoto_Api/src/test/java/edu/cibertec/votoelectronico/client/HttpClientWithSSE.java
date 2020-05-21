package edu.cibertec.votoelectronico.client;

import java.util.Map;
import java.util.function.Consumer;

import javax.ws.rs.sse.SseEventSource;

public interface HttpClientWithSSE {

	public <T> SseEventSource listenOn(String path, Map<String, Object> header, Consumer<T> consumer,
			Class<T> responseType);

}
