package com.softmint.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softmint.enums.LoanApprovalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "loan_approvals")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanApproval extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Loan loan;
    @ManyToOne
    private ApprovalStep step; // The step being processed
    private String notes;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private LoanApprovalStatus status;
}
