package com.github.ealcantara22.ticketexam.modules.organization.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrganizationRequest {

	public String name;
	public String phone;
	public String address1;
	public String address2;
	public String city;
	public String state;
	public String zip;
}
