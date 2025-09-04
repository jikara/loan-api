package com.softmint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softmint.entity.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Phone number is required")
    private String phone;
    @Valid
    private Role role;
}
