package com.softmint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "approval_policies")
public class ApprovalPolicy extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private boolean active = true;
    @OneToMany(mappedBy = "approvalPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<ApprovalStep> steps = new ArrayList<>();
}

