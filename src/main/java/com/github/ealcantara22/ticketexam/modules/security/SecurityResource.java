package com.github.ealcantara22.ticketexam.modules.security;

import com.github.ealcantara22.ticketexam.modules.security.dto.JwtResponse;
import com.github.ealcantara22.ticketexam.modules.security.dto.LoginRequest;
import com.github.ealcantara22.ticketexam.modules.user.UserService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SecurityResource {

	@Inject
	UserService userService;

	@Inject
	SecurityService securityService;

	@POST
	@Path("/login")
	@PermitAll()
	public JwtResponse login(LoginRequest loginRequest) {
		return securityService.generateJWT(userService.authenticate(loginRequest));
	}

}
