package com.github.ealcantara22.ticketexam.modules.ticket;

import com.github.ealcantara22.ticketexam.modules.ticket.status.Status;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {

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
}
