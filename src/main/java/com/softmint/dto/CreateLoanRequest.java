package com.softmint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softmint.entity.LoanDocument;
import com.softmint.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CreateLoanRequest {
    @Valid
    private Product product;
    @NotNull(message = "Principal amount is required")
    private BigDecimal principal;
    @NotNull(message = "Tenure months is required")
    private Integer tenureMonths;
    @Valid
    private List<LoanDocument> loanDocuments;
    @NotNull
    @AssertTrue(message = "You must accept the terms and conditions")
    private Boolean acceptTerms;
}
