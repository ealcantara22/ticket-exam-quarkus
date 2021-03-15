package com.github.ealcantara22.ticketexam.modules.ticket.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class StatusResponse {

	public Long id;
	public String name;
	public boolean isClosed;
}
