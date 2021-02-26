package com.github.ealcantara22.ticketexam.modules.employee;

import com.github.ealcantara22.ticketexam.modules.employee.dto.EmployeeRequest;
import com.github.ealcantara22.ticketexam.modules.user.UserService;
import com.github.ealcantara22.ticketexam.providers.validator.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
public class EmployeeService {

	@Inject
	EmployeeMapper employeeMapper;

	@Inject
	UserService userService;

	@Inject
	Validator validator;

	public Employee getById(Long id) {
		return Employee.findById(id);
	}

	public Employee create(EmployeeRequest data) {

		Employee duplicated = Employee.findByEmail(data.email);
		if (duplicated != null)
			throw new BadRequestException("Email is not available");

		Employee employee = employeeMapper.toResource(data);
		validator.validate(employee);

		// creates and associate the user and employee
		userService.create(employee, data.security);

		return employee;
	}

	public Employee update(Employee employee, EmployeeRequest data) {

		if (!employee.email.equals(data.email)) {
			Employee duplicated = Employee.findByEmail(data.email);
			if (duplicated != null && !duplicated.id.equals(employee.id))
				throw new BadRequestException("Email is not available");
		}

		employeeMapper.map(data, employee);
		validator.validate(employee);
		Employee.persist(employee);
		return employee;
	}
}
