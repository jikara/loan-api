package com.softmint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "check-off-remittances")
public class CheckOffRemittance extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Basic(optional = false)
    private BigDecimal amount;
    @Basic(optional = false)
    private String reference;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Employer employer;
    @OneToMany(mappedBy = "remittance", cascade = CascadeType.ALL)
    private List<CheckOffRemittanceDetail> details;

    public void setDetails(List<CheckOffRemittanceDetail> details) {
        for (CheckOffRemittanceDetail detail : details) {
            detail.setRemittance(this);
        }
        this.details = details;
    }
}
