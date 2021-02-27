package com.github.ealcantara22.ticketexam.modules.ticket;

import com.github.ealcantara22.ticketexam.modules.employee.Employee;
import com.github.ealcantara22.ticketexam.modules.employee.dto.EmployeeRequest;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.TicketRequest;
import com.github.ealcantara22.ticketexam.modules.user.User;
import com.github.ealcantara22.ticketexam.providers.validator.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
public class TicketService {

	@Inject
	TicketMapper ticketMapper;

	@Inject
	Validator validator;

	public Ticket getById(Long id) {
		return Ticket.findById(id);
	}

	public Ticket create(User logged, TicketRequest data) {

		// instance and validation
		Ticket ticket = ticketMapper.toResource(data);
		validator.validate(ticket);

		// additional data
		// TODO: handle list of employees (data.employees)
		ticket.createdBy = logged.employee;

		Ticket.persist(ticket);
		return ticket;
	}

	public Ticket update(User logged, Ticket ticket, TicketRequest data) {

		// instance and validation
		ticketMapper.map(data, ticket);
		validator.validate(ticket);

		// additional data
		// TODO: handle list of employees (data.employees)

		Ticket.persist(ticket);
		return ticket;
	}
}
