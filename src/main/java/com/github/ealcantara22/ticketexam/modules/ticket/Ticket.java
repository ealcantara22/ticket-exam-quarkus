package com.github.ealcantara22.ticketexam.modules.ticket;

import com.github.ealcantara22.ticketexam.modules.employee.Employee;
import com.github.ealcantara22.ticketexam.modules.ticket.status.Status;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket")
public class Ticket extends PanacheEntityBase {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_generator")
	@SequenceGenerator(name="ticket_generator", allocationSize=1, sequenceName = "seq_ticket", initialValue = 8000)
	public Long id;

	@Column(name = "subject", length = 200, nullable = false)
	@NotBlank(message = "Ticket subject field is required")
	public String subject;

	@NotNull(message = "Ticket date field is required")
	@Column(name = "ticket_date")
	public LocalDateTime date;

	@ManyToOne
	@NotNull(message = "Ticket status field is required")
	@JoinColumn(name="status_id", nullable=false)
	public Status status;

	@NotBlank(message = "Ticket description field is required")
	@Column(name = "description", columnDefinition = "TEXT", nullable = false)
	public String description;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(
		name = "ticket_employee",
		joinColumns = { @JoinColumn(name = "ticket_id") },
		inverseJoinColumns = { @JoinColumn(name = "employee_id") }
	)
	public List<Employee> employees = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name="created_employee_id", nullable=false)
	public Employee createdBy;

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
}
