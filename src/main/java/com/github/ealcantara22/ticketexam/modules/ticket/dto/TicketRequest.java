package com.github.ealcantara22.ticketexam.modules.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;
import java.util.List;

@RegisterForReflection
public class TicketRequest {

	public String subject;
	public String description;
	public Long statusId;
	public List<Long> employeeIds;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public LocalDateTime date;
}
