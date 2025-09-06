package com.softmint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "check-off-remittance-details")
public class CheckOffRemittanceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ud;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CheckOffRemittance remittance;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Loan loan;
    @Basic(optional = false)
    private BigDecimal amount;
}
