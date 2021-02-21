package com.github.ealcantara22.ticketexam.providers.validator;

import com.github.ealcantara22.ticketexam.providers.exception.InvalidEntityException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;

@ApplicationScoped
public class Validator {

	@Inject
	javax.validation.Validator validator;

	public <T> void validate(T entity) {
		Set<ConstraintViolation<T>> violations = validator.validate(entity);
		if (!violations.isEmpty()) throw new InvalidEntityException(violations);
	}
}
