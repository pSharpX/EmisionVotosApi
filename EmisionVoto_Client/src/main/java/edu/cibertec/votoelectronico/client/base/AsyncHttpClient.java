package edu.cibertec.votoelectronico.client.base;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AsyncHttpClient {

	public <T, E> CompletableFuture<T> requestAsync(String path, HttpClient.REQUEST_METHOD method, E entity,
			Map<String, Object> header, Class<T> responseType);

	public <T> CompletableFuture<T> getAsync(String path, Map<String, Object> header, Class<T> responseType);

	public <T, E> CompletableFuture<T> postAsync(String path, E entity, Map<String, Object> header,
			Class<T> responseType);

	public <T, E> CompletableFuture<T> putAsync(String path, E entity, Map<String, Object> header,
			Class<T> responseType);

	public <T, E> CompletableFuture<T> deleteAsync(String path, Map<String, Object> header, Class<T> responseType);

}
