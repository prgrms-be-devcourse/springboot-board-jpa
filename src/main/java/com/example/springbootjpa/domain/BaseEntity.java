package com.example.springbootjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private ZonedDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
