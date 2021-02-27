package com.github.ealcantara22.ticketexam.modules.ticket.status;

import javax.persistence.*;

@Entity
@Table(name = "ticket_status")
public class Status {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_status_generator")
	@SequenceGenerator(name="ticket_status_generator", allocationSize=1, sequenceName = "seq_ticket_status", initialValue = 1)
	public Long id;

	@Column(name = "name", nullable = false)
	public String name;

	@Column(name = "is_closed")
	public boolean isClosed = false;
}
