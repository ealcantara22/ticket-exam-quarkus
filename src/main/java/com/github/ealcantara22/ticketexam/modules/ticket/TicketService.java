package com.github.ealcantara22.ticketexam.modules.ticket;

import com.github.ealcantara22.ticketexam.modules.employee.Employee;
import com.github.ealcantara22.ticketexam.modules.employee.EmployeeService;
import com.github.ealcantara22.ticketexam.modules.ticket.dto.TicketRequest;
import com.github.ealcantara22.ticketexam.modules.ticket.status.Status;
import com.github.ealcantara22.ticketexam.modules.user.User;
import com.github.ealcantara22.ticketexam.providers.validator.Validator;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TicketService {

	@Inject
	EmployeeService employeeService;

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
		handleTicketEmployees(ticket.employees, data.employeeIds);
		ticket.createdBy = logged.employee;

		Ticket.persist(ticket);
		return ticket;
	}

	public Ticket update(User logged, Ticket ticket, TicketRequest data) {

		// instance and validation
		ticketMapper.map(data, ticket);
		validator.validate(ticket);

		// additional data
		handleTicketEmployees(ticket.employees, data.employeeIds);

		Ticket.persist(ticket);
		return ticket;
	}

	public List<Status> statusList() {
		return Status.listAll(Sort.by("name").ascending());
	}

	private void handleTicketEmployees(List<Employee> employees, List<Long> employeeIds) {
		// removing
		employees.removeIf(employee -> !employeeIds.contains(employee.id));

		// adding
		employeeIds.forEach(employeeId -> {
			boolean found = false;
			for (Employee employee : employees) {
				if (employee.id.equals(employeeId)) {
					found = true;
					break;
				}
			}

			if (!found) {
				Employee employee = employeeService.getById(employeeId);
				if (employee != null)
					employees.add(employee);
			}
		});
	}
}
