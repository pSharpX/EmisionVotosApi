package edu.cibertec.votoelectronico.client.base;

import java.util.Map;

public interface HttpClient<T, E> {

	public enum REQUEST_METHOD {
		GET, POST, PUT, DELETE
	}

	public T request(String path, REQUEST_METHOD method, E entity, Map<String, Object> header);

	public T get(String path, Map<String, String> header);

	public T post(String path, E entity, Map<String, String> header);

	public T put(String path, E entity, Map<String, String> header);

	public T delete(String path, Map<String, String> header);

}
