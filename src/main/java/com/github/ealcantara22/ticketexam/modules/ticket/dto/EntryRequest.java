package com.github.ealcantara22.ticketexam.modules.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;

@RegisterForReflection
public class EntryRequest {

	public String note;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public LocalDateTime startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public LocalDateTime endDate;
}
