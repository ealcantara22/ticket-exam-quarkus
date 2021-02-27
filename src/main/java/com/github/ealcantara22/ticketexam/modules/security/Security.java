package com.github.ealcantara22.ticketexam.modules.security;

import java.util.Arrays;
import java.util.List;

public class Security {

	// Security schema OpenAPI
	public static final String OPEN_API_SCHEME_NAME = "JWT";

	// Security Roles
	public static class Role {

		public static final String ROLE_BASE_USER = "ROLE_BASE_USER";
		public static final String ROLE_ADMIN_USER = "ROLE_ADMIN_USER";

		public static List<String> getApplicationRoles() {
			return Arrays.asList(ROLE_BASE_USER, ROLE_ADMIN_USER);
		}
	}
}
