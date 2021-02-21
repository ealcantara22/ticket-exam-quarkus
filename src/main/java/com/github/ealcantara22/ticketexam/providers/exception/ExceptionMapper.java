package com.github.ealcantara22.ticketexam.providers.exception;

import javax.json.Json;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		int code = 500;
		if (exception instanceof WebApplicationException) {
			code = ((WebApplicationException) exception).getResponse().getStatus();
		}

		return Response.status(code)
			.entity(Json.createObjectBuilder()
				.add("error", exception.getMessage())
				.add("code", code)
				.build())
			.build();
	}
}
