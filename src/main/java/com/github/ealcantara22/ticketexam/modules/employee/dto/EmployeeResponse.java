package com.github.ealcantara22.ticketexam.modules.employee.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class EmployeeResponse {

	public Long id;
	public String firstName;
	public String lastName;
	public String email;
	public String phone;
	public String address1;
	public String address2;
	public String city;
	public String state;
	public String zip;
	public Long userId;
}
