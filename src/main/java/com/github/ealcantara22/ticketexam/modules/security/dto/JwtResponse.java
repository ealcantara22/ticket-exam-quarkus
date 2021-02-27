package com.github.ealcantara22.ticketexam.modules.security.dto;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtResponse {

	public String token;

	public JwtResponse() {
	}

	public JwtResponse(String token) {
		this.token = token;
	}
}
