package com.github.ealcantara22.ticketexam.modules.employee;

import com.github.ealcantara22.ticketexam.modules.employee.dto.EmployeeRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

	@Inject
	EmployeeMapper employeeMapper;

	@Inject
	EmployeeService employeeService;

	@POST
	@Transactional
	public Response create(EmployeeRequest requestData) {
		return Response
			.status(201)
			.entity(employeeMapper.toResponse(employeeService.create(requestData)))
			.build();
	}
}
