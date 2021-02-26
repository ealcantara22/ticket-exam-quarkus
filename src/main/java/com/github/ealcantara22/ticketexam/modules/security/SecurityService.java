package com.github.ealcantara22.ticketexam.modules.security;

import com.github.ealcantara22.ticketexam.modules.security.dto.JwtResponse;
import com.github.ealcantara22.ticketexam.modules.user.User;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.HashSet;

@ApplicationScoped
public class SecurityService {

	@ConfigProperty(name = "mp.jwt.verify.issuer")
	String issuer;

	@ConfigProperty(name = "jwt.expiration.time.minutes")
	Integer expiration;

	public JwtResponse generateJWT(User user) {

		Instant now = Instant.now();

		return new JwtResponse(Jwt.issuer(issuer)
			.upn(user.email)
			.subject(user.email)
			.groups(new HashSet<>(user.roles))
			.issuedAt(now)
			.expiresAt(now.plusSeconds((expiration * 60)))
			.sign());
	}

}
