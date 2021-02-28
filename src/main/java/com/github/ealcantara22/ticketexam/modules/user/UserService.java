package com.github.ealcantara22.ticketexam.modules.user;

import com.github.ealcantara22.ticketexam.modules.employee.Employee;
import com.github.ealcantara22.ticketexam.modules.employee.dto.EmployeeRequest;
import com.github.ealcantara22.ticketexam.modules.security.Security;
import com.github.ealcantara22.ticketexam.modules.security.dto.LoginRequest;
import com.github.ealcantara22.ticketexam.providers.validator.Validator;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.util.ModularCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@ApplicationScoped
public class UserService {

	@Inject
	Validator validator;

	public static final String INVALID_CREDENTIALS = "Email or password incorrect, please verify your credentials";
	public static final String INACTIVE_ACCOUNT = "The account is inactive, please contact support for assistance";

	public User getById(Long id) {
		return User.findById(id);
	}

	public User getByEmail(String email) {
		return User.findByEmail(email);
	}

	public boolean isAdmin(User user) {
		return user.roles.contains(Security.Role.ROLE_ADMIN_USER);
	}

	public User create(Employee employee, EmployeeRequest.Security security) {

		User user = new User();
		user.email = security.email != null ? security.email : employee.email;
		user.isActive = true;
		user.roles = security.roles;

		// Generating and encrypting user password
		user.plainPassword = RandomStringUtils.randomAlphanumeric(20);
		user.password = BcryptUtil.bcryptHash(user.plainPassword);

		// TODO: create Roles entity or ENUM and validate
		// TODO: send user welcome email with login url and credentials after persist

		validator.validate(user);

		employee.user = user;
		user.employee = employee;

		User.persist(user);
		return user;
	}

	public User authenticate(LoginRequest data) {

		boolean isVerified;

		User user = User.findByEmail(data.email);
		if (user == null)
			throw new NotAuthorizedException(INVALID_CREDENTIALS);

		try {
			isVerified = verifyPassword(data.password, user.password);
		} catch (Exception exception) {
			throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR, exception.getCause());
		}

		if (!isVerified)
			throw new NotAuthorizedException(INVALID_CREDENTIALS);

		// validating if access is revoke
		if (!user.isActive)
			throw new BadRequestException(INACTIVE_ACCOUNT);

		user.lastAccess = LocalDateTime.now();
		user.persist();

		return user;
	}

	private boolean verifyPassword(String originalPwd, String encryptedPwd) throws Exception {
		// convert encrypted password string to a password key
		Password rawPassword = ModularCrypt.decode(encryptedPwd);

		// create the password factory based on the bcrypt algorithm
		PasswordFactory factory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT);

		// create encrypted password based on stored string
		BCryptPassword restored = (BCryptPassword) factory.translate(rawPassword);

		// verify restored password against original
		return factory.verify(restored, originalPwd.toCharArray());
	}
}
