package com.softmint.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "approval_policies")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApprovalPolicy extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Basic(optional = false)
    private String name;
    private boolean active = true;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_step_id")
    private ApprovalStep firstStep;
    // All steps belonging to this policy
    @OneToMany(mappedBy = "approvalPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApprovalStep> steps = new ArrayList<>();
}

