package com.softmint.entity;

import com.softmint.enums.InterestCalculationMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "interest_rates",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"employer_id", "product_id", "effective_from"})
        }
)
public class Interest extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    // 🔹 Negotiated financial terms
    private BigDecimal interestRate;   // e.g., 0.05 = 5% per month
    @Enumerated(EnumType.STRING)
    private InterestCalculationMethod interestType; // FLAT | REDUCING_BALANCE
    private BigDecimal processingFee;  // absolute value or percentage depending on business rule
    private BigDecimal penaltyRate;    // e.g., 0.02 = 2% penalty
    // 🔹 Validity period (for re-negotiations)
    @Basic(optional = false)
    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;
    @Column(name = "effective_to")
    private LocalDate effectiveTo;
    // 🔹 Status
    private boolean active = true;
}
