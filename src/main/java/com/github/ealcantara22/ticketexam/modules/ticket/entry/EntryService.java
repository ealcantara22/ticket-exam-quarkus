package com.github.ealcantara22.ticketexam.modules.ticket.entry;

import com.github.ealcantara22.ticketexam.providers.validator.Validator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EntryService {

	@Inject
	EntryMapper entryMapper;

	@Inject
	Validator validator;

	public Entry getById(Long id) {
		return Entry.findById(id);
	}
}
