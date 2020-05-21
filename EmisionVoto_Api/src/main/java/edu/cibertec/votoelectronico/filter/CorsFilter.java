package edu.cibertec.votoelectronico.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@PreMatching
public class CorsFilter implements ContainerResponseFilter, ContainerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(CorsFilter.class);

	private static final String HEADER_ORIGIN = "Origin";

	private static final String HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

	private static final String HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

	private static final String HEADER_ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";

	private static final String HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

	private static final String HEADER_ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";

	private static final String HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

	private Set<String> allowedOrigins = new HashSet<>(Arrays.asList("*"));

	public Set<String> getAllowedOrigins() {
		return allowedOrigins;
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		String origin = requestContext.getHeaderString(HEADER_ORIGIN);
		LOG.info("HTTP HEADER [{}] VALUE [{}]", HEADER_ORIGIN, origin);
		if (origin == null || requestContext.getMethod().equalsIgnoreCase("OPTIONS")
				|| requestContext.getProperty("cors.failure") != null) {
			return;
		}
		responseContext.getHeaders().putSingle(HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
		responseContext.getHeaders().putSingle(HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String origin = requestContext.getHeaderString(HEADER_ORIGIN);
		LOG.info("HTTP HEADER [{}] VALUE [{}]", HEADER_ORIGIN, origin);
		if (origin == null) {
			return;
		}
		if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
			preFlight(origin, requestContext);
		} else {
			checkOrigin(requestContext, origin);
		}
	}

	private void preFlight(String origin, ContainerRequestContext requestContext) throws IOException {
		checkOrigin(requestContext, origin);
		Response.ResponseBuilder builder = Response.ok();
		builder.header(HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
		builder.header(HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

		String requestMethods = requestContext.getHeaderString(HEADER_ACCESS_CONTROL_REQUEST_METHOD);
		if (requestMethods != null) {
			builder.header(HEADER_ACCESS_CONTROL_ALLOW_METHODS, requestMethods);
		}

		String allowHeaders = requestContext.getHeaderString(HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
		if (allowHeaders != null) {
			builder.header(HEADER_ACCESS_CONTROL_ALLOW_HEADERS, allowHeaders);
		}
		requestContext.abortWith(builder.build());
	}

	private void checkOrigin(ContainerRequestContext requestContext, String origin) {
		if (!allowedOrigins.contains("*") && !allowedOrigins.contains(origin)) {
			requestContext.setProperty("cors.failure", true);
			throw new ForbiddenException("Origin not allowed: " + origin);
		}
	}

}
