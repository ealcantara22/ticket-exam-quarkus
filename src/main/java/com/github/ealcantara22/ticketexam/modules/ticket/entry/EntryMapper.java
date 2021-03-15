package com.github.ealcantara22.ticketexam.modules.ticket.entry;

import com.github.ealcantara22.ticketexam.modules.ticket.dto.EntryRequest;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.EntryResponse;
import com.github.ealcantara22.ticketexam.providers.mapStruct.MapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface EntryMapper {

	EntryResponse toResponse(Entry entity);

	Entry toResource(EntryRequest requestData);

	void map(EntryRequest requestData, @MappingTarget Entry entity);
}
