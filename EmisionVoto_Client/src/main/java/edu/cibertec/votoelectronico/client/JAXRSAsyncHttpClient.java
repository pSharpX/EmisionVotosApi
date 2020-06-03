package edu.cibertec.votoelectronico.client;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.Dependent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.client.base.CommonAsyncHttpClient;
import edu.cibertec.votoelectronico.client.base.HttpClient;
import edu.cibertec.votoelectronico.client.qualifier.AsyncHttpClientQualifier;

@AsyncHttpClientQualifier
@Dependent
public class JAXRSAsyncHttpClient implements CommonAsyncHttpClient {

	private final Logger LOG = LoggerFactory.getLogger(JAXRSAsyncHttpClient.class);

	@Override
	public <T, E> CompletableFuture<T> requestAsync(String path, HttpClient.REQUEST_METHOD method, E entity,
			Map<String, Object> header, Class<T> responseType) {
		return CompletableFuture.supplyAsync(() -> {
			Client client = ClientBuilder.newBuilder().build();
			WebTarget target = client.target(path);

			Builder builder = target.request().accept(MediaType.APPLICATION_JSON);
			if (!Objects.isNull(header) && !header.isEmpty()) {
				header.forEach((key, value) -> {
					builder.header(key, value);
				});
			}

			Response response = null;
			switch (method) {
			case GET:
				response = builder.get();
				break;
			case POST:
				response = builder.post(Entity.json(entity));
				break;
			case PUT:
				response = builder.put(Entity.json(entity));
				break;
			case DELETE:
				response = builder.delete();
				break;
			default:
				response = builder.get();
				break;
			}

			T value = response.readEntity(responseType);
			response.close();

			LOG.info("[{}]", Thread.currentThread().getId());
			LOG.info(value.toString());
			return value;
		});
	}

}
