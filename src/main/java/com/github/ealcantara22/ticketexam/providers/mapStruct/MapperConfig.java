package com.github.ealcantara22.ticketexam.providers.mapStruct;

import org.mapstruct.InjectionStrategy;

@org.mapstruct.MapperConfig(
	componentModel = "cdi",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MapperConfig {
}
