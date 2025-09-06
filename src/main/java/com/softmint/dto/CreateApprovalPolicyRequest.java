package com.softmint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateApprovalPolicyRequest {
    @NotBlank
    private String name;
    @NotEmpty
    private List<CreateApprovalStepRequest> steps;
}
