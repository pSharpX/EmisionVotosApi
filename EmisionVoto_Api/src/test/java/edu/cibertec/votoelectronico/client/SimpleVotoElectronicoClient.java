package edu.cibertec.votoelectronico.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.dto.EmisionVotoDto;
import edu.cibertec.votoelectronico.resource.communication.EmisionVotoResponse;
import edu.cibertec.votoelectronico.resource.communication.ListVotoResponse;
import edu.cibertec.votoelectronico.resource.communication.ResumenProcesoResponse;

public class SimpleVotoElectronicoClient {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleVotoElectronicoClient.class);

	private CommonAsyncHttpClient httpClient;

	public void setHttpClient(CommonAsyncHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void getResumenProcesoAsync() {
		CompletableFuture<ResumenProcesoResponse> promise = httpClient
				.getAsync("http://localhost:8080/v1/votoelectronico/resultado", null, ResumenProcesoResponse.class);
		LOG.info("Thread: [{}] - Promise has being called", Thread.currentThread().getId());

		promise.thenApplyAsync((ResumenProcesoResponse response) -> {
			LOG.info(response.toString());
			LOG.info("Thread: [{}] - Promise has being resolved", Thread.currentThread().getId());
			LOG.info("Thread: [{}]", Thread.currentThread().getId());
			return response;
		}).exceptionally((exception) -> {
			LOG.error("Thread: [{}] - Exception: [{}]", Thread.currentThread().getId(), exception);
			return new ResumenProcesoResponse(exception.getMessage());
		});
	}

	public void getListadoVotosAsync() {
		CompletableFuture<ListVotoResponse> promise = httpClient.getAsync("http://localhost:8080/v1/votoelectronico",
				null, ListVotoResponse.class);
		LOG.info("Thread: [{}] - Promise has being called", Thread.currentThread().getId());

		promise.thenApplyAsync((ListVotoResponse response) -> {
			LOG.info(response.toString());
			LOG.info("Thread: [{}] - Promise has being resolved", Thread.currentThread().getId());
			LOG.info("Thread: [{}]", Thread.currentThread().getId());
			return response;
		}).exceptionally((exception) -> {
			LOG.error("Thread: [{}] - Exception: [{}]", Thread.currentThread().getId(), exception);
			return new ListVotoResponse(exception.getMessage());
		});
	}

	public void createEmitirVotoAsync() {
		EmisionVotoDto data = new EmisionVotoDto();
		data.setDni(String.format("48048%s", randomWithRange(100, 999)));
		data.setGrupoPolitico("P1");
		data.setFecha(new Date());
		CompletableFuture<EmisionVotoResponse> promise = httpClient
				.postAsync("http://localhost:8080/v1/votoelectronico/emitir", data, null, EmisionVotoResponse.class);
		LOG.info("Thread: [{}] - Promise has being called", Thread.currentThread().getId());

		promise.thenApplyAsync((EmisionVotoResponse response) -> {
			LOG.info(response.toString());
			LOG.info("Thread: [{}] - Promise has being resolved", Thread.currentThread().getId());
			LOG.info("Thread: [{}]", Thread.currentThread().getId());
			return response;
		}).exceptionally((exception) -> {
			LOG.error("Thread: [{}] - Exception: [{}]", Thread.currentThread().getId(), exception);
			return new EmisionVotoResponse(exception.getMessage());
		});
	}

	public void createCollectionEmitirVotoAsync() {
		List<EmisionVotoDto> payloads = this.createCollectionOfEmitirVoto();

		List<CompletableFuture<EmisionVotoResponse>> requestFutures = payloads.stream().map(payload -> httpClient
				.postAsync("http://localhost:8080/v1/votoelectronico/emitir", payload, null, EmisionVotoResponse.class))
				.collect(Collectors.toList());

		CompletableFuture<Void> allFutures = CompletableFuture
				.allOf(requestFutures.toArray(new CompletableFuture[requestFutures.size()]));

		@SuppressWarnings("unused")
		CompletableFuture<List<EmisionVotoResponse>> allRequestsFuture = allFutures.thenApply(v -> {
			return requestFutures.stream().map(requestFuture -> requestFuture.join()).collect(Collectors.toList());
		}).thenApply((List<EmisionVotoResponse> response) -> {
			LOG.info(response.toString());
			LOG.info("Thread: [{}] - Promise has being resolved", Thread.currentThread().getId());
			LOG.info("Thread: [{}]", Thread.currentThread().getId());
			return response;
		}).exceptionally((exception) -> {
			LOG.error("Thread: [{}] - Exception: [{}]", Thread.currentThread().getId(), exception);
			return Arrays.asList(new EmisionVotoResponse());
		});
	}

	public void createEmitirVotoThenGetListadoVotosAsyncAndGetResumenProcesoAsync() {
		EmisionVotoDto data = new EmisionVotoDto();
		data.setDni(String.format("48048%s", randomWithRange(100, 999)));
		data.setGrupoPolitico("P1");
		data.setFecha(new Date());
		CompletableFuture<EmisionVotoResponse> promise = httpClient
				.postAsync("http://localhost:8080/v1/votoelectronico/emitir", data, null, EmisionVotoResponse.class)
				.thenApply((response) -> {
					LOG.info(response.toString());
					LOG.info("Thread: [{}] - Promise has being resolved", Thread.currentThread().getId());
					return response;
				});

		promise.thenRunAsync(() -> {
			httpClient.getAsync("http://localhost:8080/v1/votoelectronico", null, ListVotoResponse.class)
					.thenApply((response) -> {
						LOG.info(response.toString());
						LOG.info("Thread: [{}] - Promise has being resolved", Thread.currentThread().getId());
						return response;
					});
		});
		promise.thenRunAsync(() -> {
			httpClient
					.getAsync("http://localhost:8080/v1/votoelectronico/resultado", null, ResumenProcesoResponse.class)
					.thenApply((response) -> {
						LOG.info(response.toString());
						LOG.info("Thread: [{}] - Promise has being resolved", Thread.currentThread().getId());
						return response;
					});
		});

	}

	private List<EmisionVotoDto> createCollectionOfEmitirVoto() {
		List<EmisionVotoDto> collection = new ArrayList<EmisionVotoDto>();
		for (int i = 0; i < 10; i++) {
			EmisionVotoDto emisionVotoDto = new EmisionVotoDto();
			emisionVotoDto.setFecha(new Date());
			emisionVotoDto.setDni(String.format("48048%s", randomWithRange(100, 999)));
			emisionVotoDto.setGrupoPolitico((i % 2) == 0 ? "P1" : "P2");
			collection.add(emisionVotoDto);
		}
		return collection;
	}

	private int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	public static void main(String[] args) {
		try {
			SeContainerInitializer initializer = SeContainerInitializer.newInstance();
			try (SeContainer container = initializer.disableDiscovery().addPackages(SimpleVotoElectronicoClient.class)
					.initialize()) {
				CommonAsyncHttpClient httpClient = container.select(JAXRSAsyncHttpClient.class).get();
				SimpleVotoElectronicoClient client = new SimpleVotoElectronicoClient();
				client.setHttpClient(httpClient);
				client.getResumenProcesoAsync();
//				client.getListadoVotosAsync();
//				client.createEmitirVotoAsync();
//				client.createCollectionEmitirVotoAsync();
//				client.createEmitirVotoThenGetListadoVotosAsyncAndGetResumenProcesoAsync();
				LOG.info("Thread: [{}]", Thread.currentThread().getId());
				LOG.info("Thread: [{}] - Running task 1", Thread.currentThread().getId());
				LOG.info("Thread: [{}] - Running task 2", Thread.currentThread().getId());
				LOG.info("Thread: [{}] - Running task 3", Thread.currentThread().getId());

				while (true) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
