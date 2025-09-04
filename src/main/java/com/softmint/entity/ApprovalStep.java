package com.softmint.entity;

import com.softmint.enums.ApprovalType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "approval_steps")
public class ApprovalStep extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "policy_id")
    private ApprovalPolicy approvalPolicy;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalType approvalType;  // EMPLOYER, LENDER, EXTERNAL, AUTO
    private int orderIndex; // Defines sequence
    /**
     * Only relevant for LENDER approval steps.
     * Null for EMPLOYER or EXTERNAL approval steps.
     */
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;
}

