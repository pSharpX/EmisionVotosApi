/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cibertec.votoelectronico.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Omar
 */
@Provider
public class LoggingClientFilter implements ClientRequestFilter, ClientResponseFilter {

	private static final Logger LOG = LoggerFactory.getLogger(LoggingClientFilter.class);
	private static final int BUFFER_SIZE = 5000;

	@Override
	public void filter(ClientRequestContext creqc) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(" - Path: ").append(creqc.getUri().getPath());
		sb.append(" - Header: ").append(creqc.getHeaders());
		sb.append(" - Entity: ").append(creqc.getEntity());
		LOG.info("HTTP REQUEST=[{}]", sb.toString());
	}

	@Override
	public void filter(ClientRequestContext creqc, ClientResponseContext cresc) throws IOException {

		StringBuilder sb = new StringBuilder();
		sb.append("Header: ").append(cresc.getHeaders());
		sb.append(" - Entity: ").append(getEntityBody(cresc));
		LOG.info("HTTP RESPONSE=[{}]", sb.toString());

	}

	private String getEntityBody(ClientResponseContext responseContext) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = responseContext.getEntityStream();

		final StringBuilder b = new StringBuilder();
		try {
			writeTo(in, out);

			byte[] requestEntity = out.toByteArray();
			if (requestEntity.length == 0) {
				b.append("").append("\n");
			} else {
				b.append(new String(requestEntity)).append("\n");
			}
			responseContext.setEntityStream(new ByteArrayInputStream(requestEntity));

		} catch (IOException ex) {
			// Handle logging error
		}
		return b.toString();
	}

	private static void writeTo(InputStream in, OutputStream out) throws IOException {
		int read;
		final byte[] data = new byte[BUFFER_SIZE];
		while ((read = in.read(data)) != -1) {
			out.write(data, 0, read);
		}
	}

}
