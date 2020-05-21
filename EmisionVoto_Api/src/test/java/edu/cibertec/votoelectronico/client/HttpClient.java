package edu.cibertec.votoelectronico.client;

import java.util.Map;

enum REQUEST_METHOD {
	GET, POST, PUT, DELETE
}

public interface HttpClient<T, E> {

	public T request(String path, REQUEST_METHOD method, E entity, Map<String, Object> header);

	public T get(String path, Map<String, String> header);

	public T post(String path, E entity, Map<String, String> header);

	public T put(String path, E entity, Map<String, String> header);

	public T delete(String path, Map<String, String> header);

}
