package com.github.ealcantara22.ticketexam.modules.user;

import com.github.ealcantara22.ticketexam.modules.employee.Employee;
import com.github.ealcantara22.ticketexam.modules.employee.dto.EmployeeRequest;
import com.github.ealcantara22.ticketexam.providers.validator.Validator;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.apache.commons.lang3.RandomStringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserService {

	@Inject
	Validator validator;

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
}
