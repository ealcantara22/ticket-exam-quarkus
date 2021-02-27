package com.github.ealcantara22.ticketexam.modules.user;

import com.github.ealcantara22.ticketexam.modules.employee.Employee;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name = "user_generator", allocationSize = 1, sequenceName = "seq_user", initialValue = 100)
	public Long id;

	@Column(name = "email", length = 100, nullable = false, unique = true)
	@Email(message = "User email is invalid ")
	@NotBlank(message = "User email is required")
	public String email;

	@Column(name = "password", nullable = false)
	public String password;

	@Transient
	public String plainPassword;

	@Column(name = "last_access")
	public LocalDateTime lastAccess;

	@Column(name = "is_active")
	public boolean isActive = true;

	@CollectionTable(
		name = "users_roles",
		joinColumns = @JoinColumn(name = "id", referencedColumnName = "id")
	)
	@Column(name = "role")
	@ElementCollection
	public List<String> roles = new ArrayList<>();

	@Column(name = "reset_password_token")
	public String resetPasswordToken;

	@Column(name = "reset_password_expiration")
	public LocalDateTime resetPasswordExpiration;

	@OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
	public Employee employee;

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

	public static User findByEmail(String email) {
		return User.find("email", email).firstResult();
	}
}
