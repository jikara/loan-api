package com.softmint.mapper;

import com.softmint.dto.CreateLoanRequest;
import com.softmint.entity.Loan;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    @Mapping(target = "id", ignore = true)
    Loan mapFromDto(CreateLoanRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLoanFromModel(Loan model, @MappingTarget Loan entity);
}
