package com.softmint.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class Auditable implements Serializable {
    @CreatedBy
    @ManyToOne
    private User createdBy;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created;
    @LastModifiedBy
    @ManyToOne
    private User lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModified;
    private Boolean deleted = false;

    public String getCreatedByDisplayName() {
        if (createdBy != null) {
            return createdBy.getFirstName() + " " + createdBy.getLastName();
        }
        return "";
    }

    public String getLastModifiedByDisplayName() {
        if (lastModifiedBy != null) {
            return lastModifiedBy.getFirstName() + " " + lastModifiedBy.getLastName();
        }
        return "";
    }
}


