package edu.cibertec.votoelectronico.client.main;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.client.JAXRSAsyncHttpClientWithSSE;
import edu.cibertec.votoelectronico.client.util.AppGetPropertyValues;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
//		test();
		testSSE();
	}

	public static void test() {
		try {
			SeContainerInitializer initializer = SeContainerInitializer.newInstance();
			try (SeContainer container = initializer.disableDiscovery()
					.addPackages(AppGetPropertyValues.class.getPackage(),
							JAXRSAsyncHttpClientWithSSE.class.getPackage(),
							SimpleSSEVotoElectronicoClient.class.getPackage())
					.initialize()) {
				SimpleVotoElectronicoClient client = container.select(SimpleVotoElectronicoClient.class).get();
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

	public static void testSSE() {
		try {
			SeContainerInitializer initializer = SeContainerInitializer.newInstance();
			try (SeContainer container = initializer.disableDiscovery()
					.addPackages(AppGetPropertyValues.class.getPackage(),
							JAXRSAsyncHttpClientWithSSE.class.getPackage(),
							SimpleSSEVotoElectronicoClient.class.getPackage())
					.initialize()) {
				SimpleSSEVotoElectronicoClient client = container.select(SimpleSSEVotoElectronicoClient.class).get();
				client.listenForResultadoProcesoUpdate();
				LOG.info("Thread: [{}]", Thread.currentThread().getId());
				LOG.info("Thread: [{}] - Running task 1", Thread.currentThread().getId());
				LOG.info("Thread: [{}] - Running task 2", Thread.currentThread().getId());

				while (true) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
