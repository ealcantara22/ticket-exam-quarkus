package com.github.ealcantara22.ticketexam.modules.employee.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.ArrayList;
import java.util.List;

@RegisterForReflection
public class EmployeeRequest {

	public String firstName;
	public String lastName;
	public String email;
	public String phone;
	public String address1;
	public String address2;
	public String city;
	public String state;
	public String zip;
	public Security security;

	public static class Security {
		public String email;
		public List<String> roles = new ArrayList<>();
	}
}
