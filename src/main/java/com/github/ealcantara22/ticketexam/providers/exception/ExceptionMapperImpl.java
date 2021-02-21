package com.github.ealcantara22.ticketexam.providers.exception;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Set;

@Provider
public class ExceptionMapperImpl implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		int code = 500;
		Set<? extends ConstraintViolation<?>> violations = null;

		if (exception instanceof WebApplicationException) {
			code = ((WebApplicationException) exception).getResponse().getStatus();
			if (exception instanceof InvalidEntityException) {
				violations = ((InvalidEntityException) exception).getViolations();
			}
		}

		// base response object
		JsonObjectBuilder responseBody = Json.createObjectBuilder()
			.add("code", code)
			.add("error", exception.getMessage());

		// validation errors
		if (violations != null) {

			JsonArrayBuilder violationsArray = Json.createArrayBuilder();
			for (ConstraintViolation cv : violations) {
				String field = null;
				for (Path.Node node : cv.getPropertyPath()) {
					field = node.getName();
				}
				violationsArray.add(Json.createObjectBuilder().add(field, cv.getMessage()));
			}
			responseBody.add("violations", violationsArray);
		}

		return Response
			.status(code)
			.entity(responseBody.build())
			.build();
	}
}
