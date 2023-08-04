package com.example.springbootjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
