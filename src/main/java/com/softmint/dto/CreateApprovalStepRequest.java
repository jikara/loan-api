package com.softmint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateApprovalStepRequest {
    @NotBlank
    private String name;
    @NotNull
    private UUID assignedRoleId; // just the role reference
}
