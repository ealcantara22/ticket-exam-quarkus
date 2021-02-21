package com.github.ealcantara22.ticketexam.modules.organization;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "organization")
public class Organization extends PanacheEntityBase {

	public static final String ORGANIZATION_ID = ConfigProvider
		.getConfig()
		.getValue("application.organization.id", String.class);

	@Id
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	public String id;

	@NotBlank(message = "Name field is required")
	@Column(name = "name", nullable = false)
	public String name;

	@Column(name = "phone", nullable = false)
	@NotBlank(message = "Phone field is required")
	public String phone;

	@Column(name = "address_1", nullable = false)
	@NotBlank(message = "Address field is required")
	public String address1;

	@Column(name = "address_2")
	public String address2;

	@Column(name = "city", nullable = false)
	@NotBlank(message = "City field is required")
	public String city;

	@Column(name = "state", nullable = false)
	@NotBlank(message = "State field is required")
	public String state;

	@Column(name = "zip_code", nullable = false)
	@NotBlank(message = "Zip Code field is required")
	public String zip;

	// TODO: organization logo once the file uploader is ready

	/**
	 * Fetch the only organization from db
	 *
	 * @return Organization
	 */
	public static Organization fetchDefault() {
		return Organization.findById(ORGANIZATION_ID);
	}

	@PrePersist
	public void setId() {
		this.id = ORGANIZATION_ID;
	}
}
