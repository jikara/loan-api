package com.softmint.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softmint.enums.InterestCalculationMethod;
import com.softmint.enums.RepaymentFrequency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private boolean active;
    // Product rules/constraints
    @Basic(optional = false)
    private BigDecimal defaultInterestRate;
    private Double maxAmountSalaryRatio;
    private BigDecimal minSalary;
    private BigDecimal minPrincipal;
    private BigDecimal maxPrincipal;
    private Integer maxRepaymentMonths;
    private boolean earlySettlementAllowed;
    private boolean rolloverAllowed;
    @Enumerated(EnumType.STRING)
    private InterestCalculationMethod interestMethod; // FLAT or REDUCING
    private BigDecimal processingFeeRate;       // % of principal
    private BigDecimal latePenaltyRate;         // % per month (optional)
    @Enumerated(EnumType.STRING)
    private RepaymentFrequency repaymentFrequency; // e.g. MONTHLY
    @OneToMany(mappedBy = "product")
    private List<Interest> negotiatedInterests;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductDocument> documents;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_policy_id")
    private ApprovalPolicy approvalPolicy;


    public void setDocuments(List<ProductDocument> documents) {
        for (ProductDocument document : documents) {
            document.setProduct(this);
        }
        this.documents = documents;
    }

    public String getApprovalPolicyName() {
        if (approvalPolicy != null) {
            return approvalPolicy.getName();
        }
        return null;
    }
}
