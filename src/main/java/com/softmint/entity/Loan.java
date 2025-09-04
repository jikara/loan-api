package com.softmint.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softmint.enums.LoanLifecycleStatus;
import com.softmint.enums.LoanPerformanceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "loans")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Loan extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Basic(optional = false)
    private String reference;
    private String idNumber;
    private String kraPin;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee borrower;
    // Loan details
    private BigDecimal principal;
    private BigDecimal effectiveInterestRate;      // effective rate (copied at approval)
    private Integer tenureMonths;            // chosen repayment period
    private LocalDate startDate;
    private LocalDate endDate;

    // Financials
    private BigDecimal disbursedAmount;      // principal - fees
    private BigDecimal totalRepayable;       // principal + interest
    private BigDecimal installmentAmount;    // per repayment period
    private BigDecimal outstandingBalance;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private LoanLifecycleStatus lifecycleStatus = LoanLifecycleStatus.PENDING;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private LoanPerformanceStatus performanceStatus = LoanPerformanceStatus.PENDING;
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LoanDocument> loanDocuments;

    public void setLoanDocuments(List<LoanDocument> loanDocuments) {
        for (LoanDocument loanDocument : loanDocuments) {
            loanDocument.setLoan(this);
        }
        this.loanDocuments = loanDocuments;
    }
}
