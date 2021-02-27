package com.github.ealcantara22.ticketexam.modules.security.dto;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotBlank;

@ApplicationScoped
public class LoginRequest {

	@NotBlank(message = "Email field is required")
	public String email;

	@NotBlank(message = "Password field is required")
	public String password;
}
