package edu.cibertec.votoelectronico.client.base;

import java.util.Map;
import java.util.function.Consumer;

import javax.ws.rs.sse.SseEventSource;

public interface HttpClientWithSSE extends CommonAsyncHttpClient {

	public <T> SseEventSource listenOn(String path, Map<String, Object> header, Consumer<T> consumer,
			Class<T> responseType);

}
