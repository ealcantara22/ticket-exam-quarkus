package com.github.ealcantara22.ticketexam.modules.ticket.entry;

import com.github.ealcantara22.ticketexam.modules.security.Security;
import com.github.ealcantara22.ticketexam.modules.ticket.Ticket;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.EntryResponse;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.TicketResponse;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
}
