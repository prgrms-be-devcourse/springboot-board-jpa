package com.programmers.springbootboardjpa.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime cratedAt;

    protected BaseEntity() {}

    public BaseEntity(String createdBy, LocalDateTime cratedAt) {
        this.createdBy = createdBy;
        this.cratedAt = cratedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCratedAt() {
        return cratedAt;
    }
}