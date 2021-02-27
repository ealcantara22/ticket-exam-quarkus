package com.github.ealcantara22.ticketexam.modules.organization;

import com.github.ealcantara22.ticketexam.modules.organization.dto.OrganizationRequest;
import com.github.ealcantara22.ticketexam.modules.organization.dto.OrganizationResponse;
import com.github.ealcantara22.ticketexam.modules.security.Security;
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

@Path("/api/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({Security.Role.ROLE_ADMIN_USER})
@SecuritySchemes(value = {
	@SecurityScheme(securitySchemeName = Security.OPEN_API_SCHEME_NAME,
		type = SecuritySchemeType.HTTP,
		scheme = "Bearer")}
)
public class OrganizationResource {

	@Inject
	OrganizationMapper organizationMapper;

	@Inject
	OrganizationService organizationService;

	@POST
	@Transactional
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public Response create(OrganizationRequest requestData) {
		return Response
			.status(201)
			.entity(organizationMapper.toResponse(organizationService.create(requestData)))
			.build();
	}

	@PUT
	@Transactional
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public OrganizationResponse update(OrganizationRequest data) {
		Organization organization = Organization.fetchDefault();
		if (organization == null)
			throw new NotFoundException("Organization not found");

		return organizationMapper.toResponse(organizationService.update(organization, data));
	}
}
