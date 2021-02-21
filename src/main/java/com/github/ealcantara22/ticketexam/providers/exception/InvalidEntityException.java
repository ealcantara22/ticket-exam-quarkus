package com.github.ealcantara22.ticketexam.providers.exception;

import javax.validation.ConstraintViolation;
import javax.ws.rs.BadRequestException;
import java.util.Set;

public class InvalidEntityException extends BadRequestException {

	private Set<? extends ConstraintViolation<?>> violations;

	public InvalidEntityException() {
	}

	public <T> InvalidEntityException(Set<ConstraintViolation<T>> violations) {
		this.violations = violations;
	}

	public Set<? extends ConstraintViolation<?>> getViolations() {
		return violations;
	}
}
