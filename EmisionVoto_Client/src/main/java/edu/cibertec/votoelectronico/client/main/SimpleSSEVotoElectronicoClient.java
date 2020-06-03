package edu.cibertec.votoelectronico.client.main;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.client.base.HttpClientWithSSE;
import edu.cibertec.votoelectronico.client.communication.ResumenProcesoResponse;
import edu.cibertec.votoelectronico.client.util.AppGetPropertyValues;

@ApplicationScoped
public class SimpleSSEVotoElectronicoClient {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleSSEVotoElectronicoClient.class);

	@Inject
	private HttpClientWithSSE httpClient;
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

	public void listenForResultadoProcesoUpdate() {
		this.httpClient.listenOn(String.format("%s/votoelectronico/subscribe/resultado", this.endpoint), null,
				(response) -> {
					LOG.info(response.toString());
				}, ResumenProcesoResponse.class);
	}

}
