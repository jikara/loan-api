package com.softmint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "documents")
public class Document extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;   // e.g., "ID_COPY", "PAYSLIP"

    @Column(nullable = false)
    private String name;   // e.g., "National ID Copy", "Latest Payslip"

    private String description;

    // Must have this for the join
    @OneToMany(mappedBy = "document")
    private List<ProductDocument> productDocuments;
}
