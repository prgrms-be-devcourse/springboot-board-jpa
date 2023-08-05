package com.jpaboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_at")
    String createdAt;

    @Column(name = "created_by", columnDefinition = "TIMESTAMP")
    String createdBy;

    public void changeCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void changeCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
