package com.github.ealcantara22.ticketexam.modules.ticket.status.converter;

import com.github.ealcantara22.ticketexam.modules.ticket.status.Status;
import org.mapstruct.TargetType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StatusConverter {
	public Status resolve(Long statusId, @TargetType Class<Status> target) {
		if (statusId != null) {
			return Status.findById(statusId);
		}
		return null;
	}
}
