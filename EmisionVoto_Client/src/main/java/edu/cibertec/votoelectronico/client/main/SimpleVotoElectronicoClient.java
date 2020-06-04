package edu.cibertec.votoelectronico.client.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.client.base.CommonAsyncHttpClient;
import edu.cibertec.votoelectronico.client.communication.EmisionVotoResponse;
import edu.cibertec.votoelectronico.client.communication.ListVotoResponse;
import edu.cibertec.votoelectronico.client.communication.ResumenProcesoResponse;
import edu.cibertec.votoelectronico.client.dto.EmisionVotoDto;
import edu.cibertec.votoelectronico.client.util.AppGetPropertyValues;

@ApplicationScoped
public class SimpleVotoElectronicoClient {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleVotoElectronicoClient.class);

	@Inject
	private CommonAsyncHttpClient httpClient;
	@Inject
	private AppGetPropertyValues props;

	private String endpoint;

	private final String CONST_VOTOELECTRONICO_API_HOSTNAME = "edu.cibertec.votoelectronico.hostname";
	private final String CONST_VOTOELECTRONICO_API_PATH = "edu.cibertec.votoelectronico.path";

	@PostConstruct
	public void init() throws IOException {
		Properties properties = this.props.getPropValues();
		this.endpoint = String.format("%s%s", properties.get(CONST_VOTOELECTRONICO_API_HOSTNAME),
				properties.get(CONST_VOTOELECTRONICO_API_PATH));
	}

	public void getResumenProcesoAsync() {
		CompletableFuture<ResumenProcesoResponse> promise = httpClient.getAsync(
				String.format("%s/votoelectronico/resultado", this.endpoint), null, ResumenProcesoResponse.class);
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
		data.setFecha((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
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
		data.setFecha((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
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
			emisionVotoDto.setFecha((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
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

}
