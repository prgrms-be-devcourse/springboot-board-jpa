package com.programmers.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String createdBy;

    protected BaseEntity() {
    }

    protected BaseEntity(String createdBy) {
        this.createdAt = LocalDateTime.now();
        this.createdBy = createdBy;
    }
}
