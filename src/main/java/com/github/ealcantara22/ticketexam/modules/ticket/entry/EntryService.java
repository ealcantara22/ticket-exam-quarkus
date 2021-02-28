package com.github.ealcantara22.ticketexam.modules.ticket.entry;

import com.github.ealcantara22.ticketexam.modules.ticket.dto.EntryRequest;
import com.github.ealcantara22.ticketexam.modules.user.User;
import com.github.ealcantara22.ticketexam.providers.validator.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
public class EntryService {

	@Inject
	EntryMapper entryMapper;

	@Inject
	Validator validator;

	public static final String ENTRY_NOT_OWNER_EDIT = "You can not modify other users entries";
	public static final String ENTRY_NOT_OWNER_DELETE = "You can not delete other users entries";

	public Entry getById(Long id) {
		return Entry.findById(id);
	}

	public Entry create(User logged, EntryRequest data) {

		// instance and validation
		Entry entry = entryMapper.toResource(data);
		validator.validate(entry);

		// additional data
		entry.employee = logged.employee;

		Entry.persist(entry);
		return entry;
	}

	public Entry update(User logged, Entry entry, EntryRequest data) {

		if (!logged.employee.equals(entry.employee))
			throw new BadRequestException(ENTRY_NOT_OWNER_EDIT);

		// instance and validation
		entryMapper.map(data, entry);
		validator.validate(entry);

		Entry.persist(entry);
		return entry;
	}

	public boolean delete(User logged, Entry entry) {
		if (!logged.employee.equals(entry.employee))
			throw new BadRequestException(ENTRY_NOT_OWNER_DELETE);

		return Entry.deleteById(entry.id);
	}
}
