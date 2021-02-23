package com.github.ealcantara22.ticketexam.modules.organization;

import com.github.ealcantara22.ticketexam.modules.organization.dto.OrganizationRequest;
import com.github.ealcantara22.ticketexam.modules.organization.dto.OrganizationResponse;
import com.github.ealcantara22.ticketexam.providers.mapStruct.MapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface OrganizationMapper {

	OrganizationResponse toResponse(Organization entity);

	Organization toResource(OrganizationRequest requestData);

	void map(OrganizationRequest requestData, @MappingTarget Organization entity);
}
