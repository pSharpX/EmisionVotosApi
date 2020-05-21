package edu.cibertec.votoelectronico.client;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.resource.communication.ResumenProcesoResponse;

public class SimpleSSEVotoElectronicoClient {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleSSEVotoElectronicoClient.class);

	private HttpClientWithSSE httpClient;

	public void setHttpClient(HttpClientWithSSE httpClient) {
		this.httpClient = httpClient;
	}

	public void listenForResultadoProcesoUpdate() {
		this.httpClient.listenOn("http://localhost:8080/api/v1/votoelectronico/subscribe/resultado", null,
				(response) -> {
					LOG.info(response.toString());
				}, ResumenProcesoResponse.class);
	}

	public static void main(String[] args) {
		try {
			SeContainerInitializer initializer = SeContainerInitializer.newInstance();
			try (SeContainer container = initializer.disableDiscovery()
					.addPackages(SimpleSSEVotoElectronicoClient.class).initialize()) {
				// start the container, retrieve a bean and do work with it
				HttpClientWithSSE httpClient = container.select(JAXRSAsyncHttpClientWithSSE.class).get();
				SimpleSSEVotoElectronicoClient client = new SimpleSSEVotoElectronicoClient();
				client.setHttpClient(httpClient);
				client.listenForResultadoProcesoUpdate();
				LOG.info("Thread: [{}]", Thread.currentThread().getId());
				LOG.info("Thread: [{}] - Running task 1", Thread.currentThread().getId());
				LOG.info("Thread: [{}] - Running task 2", Thread.currentThread().getId());

				while (true) {
				}
			}
			// shuts down automatically after the try with resources block.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
