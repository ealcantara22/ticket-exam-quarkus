package com.github.ealcantara22.ticketexam.modules.organization;

import com.github.ealcantara22.ticketexam.modules.organization.dto.OrganizationRequest;
import com.github.ealcantara22.ticketexam.modules.organization.dto.OrganizationResponse;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationResource {

	@Inject
	OrganizationMapper organizationMapper;

	@Inject
	OrganizationService organizationService;

	@POST
	@Transactional
	public Response create(OrganizationRequest requestData) {
		return Response
			.status(201)
			.entity(organizationMapper.toResponse(organizationService.create(requestData)))
			.build();
	}

	@PUT
	@Transactional
	public OrganizationResponse update(OrganizationRequest data) {
		Organization organization = Organization.fetchDefault();
		if (organization == null)
			throw new NotFoundException("Organization not found");

		return organizationMapper.toResponse(organizationService.update(organization, data));
	}
}
