package com.github.ealcantara22.ticketexam.modules.organization;

import com.github.ealcantara22.ticketexam.modules.organization.dto.OrganizationRequest;
import com.github.ealcantara22.ticketexam.providers.validator.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
public class OrganizationService {

	@Inject
	OrganizationMapper organizationMapper;

	@Inject
	Validator validator;

	public Organization create(OrganizationRequest data) {

		Organization organization = Organization.fetchDefault();
		if (organization != null)
			throw new BadRequestException("Can not create multiple organizations");

		organization = organizationMapper.toResource(data);
		validator.validate(organization);
		Organization.persist(organization);

		return organization;
	}

	public Organization update(Organization organization, OrganizationRequest data) {

		organizationMapper.map(data, organization);
		validator.validate(organization);
		Organization.persist(organization);

		return organization;
	}
}
