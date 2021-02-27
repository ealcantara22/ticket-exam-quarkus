package com.github.ealcantara22.ticketexam.modules.employee.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class EmployeeBaseResponse {

	public Long id;
	public String firstName;
	public String lastName;
	public String email;
}
