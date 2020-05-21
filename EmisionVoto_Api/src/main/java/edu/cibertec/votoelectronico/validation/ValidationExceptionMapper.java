package edu.cibertec.votoelectronico.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.cibertec.votoelectronico.resource.communication.ValidationResponse;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		Response.ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		Map<String, String> validations = new HashMap<String, String>();
		exception.getConstraintViolations().forEach(v -> {
			validations.put(StreamSupport.stream(v.getPropertyPath().spliterator(), false)
					.reduce((first, second) -> second).orElse(null).getName(), v.getMessage());
			builder.header("Error-Description", v.getMessage());
		});
		return builder.entity(new ValidationResponse(validations)).build();
	}

}
