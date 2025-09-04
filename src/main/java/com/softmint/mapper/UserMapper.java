package com.softmint.mapper;

import com.softmint.dto.CreateUserRequest;
import com.softmint.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User mapFromDto(CreateUserRequest dto);
}
