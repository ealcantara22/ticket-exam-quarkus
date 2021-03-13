package com.github.ealcantara22.ticketexam.modules.ticket.entry;

import com.github.ealcantara22.ticketexam.modules.security.Security;
import com.github.ealcantara22.ticketexam.modules.security.SecurityService;
import com.github.ealcantara22.ticketexam.modules.ticket.Ticket;
import com.github.ealcantara22.ticketexam.modules.ticket.TicketResource;
import com.github.ealcantara22.ticketexam.modules.ticket.TicketService;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.EntryRequest;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.EntryResponse;
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

@Path("/api/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({Security.Role.ROLE_ADMIN_USER, Security.Role.ROLE_BASE_USER})
@SecuritySchemes(value = {
	@SecurityScheme(securitySchemeName = Security.OPEN_API_SCHEME_NAME,
		type = SecuritySchemeType.HTTP,
		scheme = "Bearer")}
)
public class EntryResource {

	@Inject
	EntryMapper entryMapper;

	@Inject
	EntryService entryService;

	@Inject
	SecurityService securityService;

	@Inject
	TicketService ticketService;

	public static final String ENTRY_NOT_FOUND = "Entry not found";

	@GET
	@Path("/{ticketId}/entries/{id}")
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public EntryResponse getById(@PathParam("ticketId") Long ticketId, @PathParam("id") Long id) {

		Entry entity = entryService.getById(id);
		if (entity == null || !ticketId.equals(entity.ticket.id))
			throw new NotFoundException(ENTRY_NOT_FOUND);

		return entryMapper.toResponse(entity);
	}

	@POST
	@Path("/{ticketId}/entries")
	@Transactional
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public Response create(@PathParam("ticketId") Long ticketId, EntryRequest requestData) {

		Ticket ticket = ticketService.getById(ticketId);
		if (ticket == null)
			throw new NotFoundException(TicketResource.TICKET_NOT_FOUND);

		Entry entity = entryService.create(securityService.getLoggedUser(), ticket, requestData);
		return Response
			.status(201)
			.entity(entryMapper.toResponse(entity))
			.build();
	}

	@PUT
	@Path("/{ticketId}/entries/{id}")
	@Transactional
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public EntryResponse update(@PathParam("ticketId") Long ticketId, @PathParam("id") Long id, EntryRequest requestData) {

		Entry entity = entryService.getById(id);
		if (entity == null || !ticketId.equals(entity.ticket.id))
			throw new NotFoundException(ENTRY_NOT_FOUND);

		User logged = securityService.getLoggedUser();

		return entryMapper.toResponse(entryService.update(logged, entity, requestData));
	}

	@DELETE
	@Path("/{ticketId}/entries/{id}")
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	@Transactional
	public Response delete(@PathParam("ticketId") Long ticketId, @PathParam("id") Long id) {

		Entry entity = entryService.getById(id);
		if (entity == null || !ticketId.equals(entity.ticket.id))
			throw new NotFoundException(ENTRY_NOT_FOUND);

		boolean deleted = entryService.delete(securityService.getLoggedUser(), entity);
		return Response
			.noContent()
			.build();
	}
}
