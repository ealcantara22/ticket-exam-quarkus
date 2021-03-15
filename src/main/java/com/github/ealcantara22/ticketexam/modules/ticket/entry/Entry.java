package com.github.ealcantara22.ticketexam.modules.ticket.entry;

import com.github.ealcantara22.ticketexam.modules.employee.Employee;
import com.github.ealcantara22.ticketexam.modules.ticket.Ticket;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_entry")
public class Entry extends PanacheEntityBase {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_entry_generator")
	@SequenceGenerator(name="ticket_entry_generator", allocationSize=1, sequenceName = "seq_ticket_entry", initialValue = 1)
	public Long id;

	@NotNull(message = "Ticket entry start date field is required")
	@Column(name = "start_date", nullable = false)
	public LocalDateTime startDate;

	@NotNull(message = "Ticket entry end date field is required")
	@Column(name = "end_date", nullable = false)
	public LocalDateTime endDate;

	@NotBlank(message = "Ticket entry note field is required")
	@Column(name = "note", columnDefinition = "TEXT", nullable = false)
	public String note;

	@ManyToOne
	@NotNull(message = "Ticket field is required")
	@JoinColumn(name="ticket_id", nullable=false)
	public Ticket ticket;

	@ManyToOne
	@NotNull(message = "Ticket entry employee field is required")
	@JoinColumn(name="employee_id", nullable=false)
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
}
