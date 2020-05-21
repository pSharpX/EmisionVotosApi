package edu.cibertec.votoelectronico.client;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface CommonAsyncHttpClient extends AsyncHttpClient {

	@Override
	default <T> CompletableFuture<T> getAsync(String path, Map<String, Object> header, Class<T> responseType) {
		return requestAsync(path, REQUEST_METHOD.GET, null, header, responseType);
	}

	@Override
	default <T, E> CompletableFuture<T> postAsync(String path, E entity, Map<String, Object> header,
			Class<T> responseType) {
		return requestAsync(path, REQUEST_METHOD.POST, entity, header, responseType);
	}

	@Override
	default <T, E> CompletableFuture<T> putAsync(String path, E entity, Map<String, Object> header,
			Class<T> responseType) {
		return requestAsync(path, REQUEST_METHOD.PUT, entity, header, responseType);
	}

	@Override
	default <T, E> CompletableFuture<T> deleteAsync(String path, Map<String, Object> header, Class<T> responseType) {
		return requestAsync(path, REQUEST_METHOD.DELETE, null, header, responseType);
	}

}
