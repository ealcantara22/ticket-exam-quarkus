package com.github.ealcantara22.ticketexam.modules.employee;

import com.github.ealcantara22.ticketexam.modules.user.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class Employee extends PanacheEntityBase {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_generator")
	@SequenceGenerator(name="employee_generator", allocationSize=1, sequenceName = "seq_employee", initialValue = 100)
	public Long id;

	@Column(name = "email", length = 100, nullable = false, unique = true)
	@Email(message = "Employee email is invalid ")
	@NotBlank(message = "Employee email is required")
	public String email;

	@Column(name = "first_name", length = 100, nullable = false)
	@NotBlank(message = "Employee firstName is required")
	public String firstName;

	@Column(name = "last_name", length = 100, nullable = false)
	@NotBlank(message = "Employee lastName is required")
	public String lastName;

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

	@OneToOne()
	@JoinColumn(name = "user_id", nullable = false)
	public User user;

	@Column(name = "created_dt")
	public LocalDateTime created;

	@Column(name = "updated_dt")
	public LocalDateTime updated;

	@PrePersist
	public void prePersist() {
		created = LocalDateTime.now();
		updated = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		updated = LocalDateTime.now();
	}

	public static Employee findByEmail(String email) {
		return Employee
			.find("email = :email OR user.email = :email", Parameters.with("email", email))
			.firstResult();
	}
}
