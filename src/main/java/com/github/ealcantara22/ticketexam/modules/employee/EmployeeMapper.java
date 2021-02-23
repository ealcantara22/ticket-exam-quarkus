package com.github.ealcantara22.ticketexam.modules.employee;

import com.github.ealcantara22.ticketexam.modules.employee.dto.EmployeeRequest;
import com.github.ealcantara22.ticketexam.modules.employee.dto.EmployeeResponse;
import com.github.ealcantara22.ticketexam.providers.mapStruct.MapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface EmployeeMapper {

	@Mapping(source = "user.id", target = "userId")
	EmployeeResponse toResponse(Employee entity);

	Employee toResource(EmployeeRequest requestData);

	void map(EmployeeRequest requestData, @MappingTarget Employee entity);
}
