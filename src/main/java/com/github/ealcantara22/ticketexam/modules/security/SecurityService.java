package com.github.ealcantara22.ticketexam.modules.security;

import com.github.ealcantara22.ticketexam.modules.security.dto.JwtResponse;
import com.github.ealcantara22.ticketexam.modules.user.User;
import com.github.ealcantara22.ticketexam.modules.user.UserService;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HashSet;

@ApplicationScoped
public class SecurityService {

	@ConfigProperty(name = "mp.jwt.verify.issuer")
	String issuer;

	@ConfigProperty(name = "jwt.expiration.time.minutes")
	Integer expiration;

	@Inject
	UserService userService;

	@Inject
	SecurityIdentity identity;

	static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom secureRandom = new SecureRandom();

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

	public User getLoggedUser() {
		if (identity.getPrincipal() == null)
			throw new NotAuthorizedException(Response.Status.UNAUTHORIZED);

		return userService.getByEmail(identity.getPrincipal().getName());
	}

	/**
	 * @link https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
	 */
	public static String generateRandomBase64Token(int byteLength) {
		byte[] token = new byte[byteLength];
		secureRandom.nextBytes(token);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(token); //base64 encoding
	}

	/**
	 * @link https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
	 */
	public static String generateRandomString(int length){
		StringBuilder sb = new StringBuilder(length);
		for(int i = 0; i < length; i++)
			sb.append(ALPHANUMERIC.charAt(secureRandom.nextInt(ALPHANUMERIC.length())));
		return sb.toString();
	}

}
