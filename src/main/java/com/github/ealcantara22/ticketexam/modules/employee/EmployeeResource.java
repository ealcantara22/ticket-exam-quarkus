package com.github.ealcantara22.ticketexam.modules.employee;

import com.github.ealcantara22.ticketexam.modules.employee.dto.EmployeeRequest;
import com.github.ealcantara22.ticketexam.modules.employee.dto.EmployeeResponse;
import com.github.ealcantara22.ticketexam.modules.security.Security;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({Security.Role.ROLE_ADMIN_USER, Security.Role.ROLE_BASE_USER})
@SecuritySchemes(value = {
	@SecurityScheme(securitySchemeName = Security.OPEN_API_SCHEME_NAME,
		type = SecuritySchemeType.HTTP,
		scheme = "Bearer")}
)
public class EmployeeResource {

	@Inject
	EmployeeMapper employeeMapper;

	@Inject
	EmployeeService employeeService;

	public static final String EMPLOYEE_NOT_FOUND = "Employee not found";

	@GET
	@Path("/{id}")
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public EmployeeResponse getById(@PathParam("id") Long id) {
		Employee entity = employeeService.getById(id);
		if (entity == null)
			throw new NotFoundException(EMPLOYEE_NOT_FOUND);

		return employeeMapper.toResponse(entity);
	}

	@POST
	@Transactional
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public Response create(EmployeeRequest requestData) {
		return Response
			.status(201)
			.entity(employeeMapper.toResponse(employeeService.create(requestData)))
			.build();
	}

	@PUT
	@Path("/{id}")
	@Transactional
	@SecurityRequirement(name = Security.OPEN_API_SCHEME_NAME)
	public EmployeeResponse update(@PathParam("id") Long id, EmployeeRequest requestData) {

		Employee entity = employeeService.getById(id);
		if (entity == null)
			throw new NotFoundException(EMPLOYEE_NOT_FOUND);

		return employeeMapper.toResponse(employeeService.update(entity, requestData));
	}
}
