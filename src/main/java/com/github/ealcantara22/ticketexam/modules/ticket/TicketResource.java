package com.github.ealcantara22.ticketexam.modules.ticket;

import com.github.ealcantara22.ticketexam.modules.security.Security;
import com.github.ealcantara22.ticketexam.modules.security.SecurityService;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.StatusResponse;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.TicketRequest;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.TicketResponse;
import com.github.ealcantara22.ticketexam.modules.ticket.status.Status;
import com.github.ealcantara22.ticketexam.modules.user.User;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({Security.Role.ROLE_ADMIN_USER, Security.Role.ROLE_BASE_USER})
@SecuritySchemes(value = {
	@SecurityScheme(securitySchemeName = Security.OPEN_API_SCHEME_NAME,
		type = SecuritySchemeType.HTTP,
		scheme = "Bearer")}
)
public class TicketResource {

	@Inject
	TicketMapper ticketMapper;

	@Inject
	TicketService ticketService;

	@Inject
	SecurityService securityService;

	public static final String TICKET_NOT_FOUND = "Ticket not found";

	@GET
	@Path("/{id}")
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public TicketResponse getById(@PathParam("id") Long id) {

		Ticket entity = ticketService.getById(id);
		if (entity == null)
			throw new NotFoundException(TICKET_NOT_FOUND);

		return ticketMapper.toResponse(entity);
	}

	@POST
	@Transactional
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public Response create(TicketRequest requestData) {

		Ticket ticket = ticketService.create(securityService.getLoggedUser(), requestData);
		return Response
			.status(201)
			.entity(ticketMapper.toResponse(ticket))
			.build();
	}

	@PUT
	@Path("/{id}")
	@Transactional
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public TicketResponse update(@PathParam("id") Long id, TicketRequest requestData) {

		Ticket entity = ticketService.getById(id);
		if (entity == null)
			throw new NotFoundException(TICKET_NOT_FOUND);

		User logged = securityService.getLoggedUser();

		return ticketMapper.toResponse(ticketService.update(logged, entity, requestData));
	}

	@GET
	@Path("/status")
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public List<StatusResponse> statusList() {
		return ticketMapper.toResponse(ticketService.statusList());
	}

}
