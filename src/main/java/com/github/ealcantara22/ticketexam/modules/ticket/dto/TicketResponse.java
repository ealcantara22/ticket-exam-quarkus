package com.github.ealcantara22.ticketexam.modules.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.ealcantara22.ticketexam.modules.employee.dto.EmployeeBaseResponse;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;
import java.util.List;

@RegisterForReflection
public class TicketResponse {

	public Long id;
	public String subject;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public LocalDateTime date;
	public String description;
	public StatusResponse status;
	public EmployeeBaseResponse createdBy;
	public List<EmployeeBaseResponse> employees;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public LocalDateTime created;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public LocalDateTime updated;
}
