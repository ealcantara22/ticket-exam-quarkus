package com.github.ealcantara22.ticketexam.modules.ticket;

import com.github.ealcantara22.ticketexam.modules.ticket.dto.TicketRequest;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.TicketResponse;
import com.github.ealcantara22.ticketexam.modules.ticket.status.converter.StatusConverter;
import com.github.ealcantara22.ticketexam.providers.mapStruct.MapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
	config = MapperConfig.class,
	uses = {
		StatusConverter.class
	})
public interface TicketMapper {

	TicketResponse toResponse(Ticket entity);

	@Mapping(source = "statusId", target = "status")
	Ticket toResource(TicketRequest requestData);

	void map(TicketRequest requestData, @MappingTarget Ticket entity);
}
