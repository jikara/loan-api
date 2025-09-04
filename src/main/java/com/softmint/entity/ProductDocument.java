package com.softmint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product_documents",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "document_id"})
        })
public class ProductDocument extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(optional = false)
    @JoinColumn(name = "document_id")
    private Document document;
    private boolean mandatory = true;   // true = must upload, false = optional
    private boolean active = true;
}

