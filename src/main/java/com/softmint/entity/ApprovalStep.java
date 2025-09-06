package com.softmint.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String name; // e.g., "Credit Check", "Manager Approval"
    private int sequence; // optional, for ordering
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_step_id")
    @JsonManagedReference
    private ApprovalStep nextStep;
    @OneToOne(mappedBy = "nextStep")
    @JsonBackReference
    private ApprovalStep previousStep;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Role assignedRole;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_policy_id")
    @JsonIgnore
    private ApprovalPolicy approvalPolicy; // each step belongs to a policy
}

