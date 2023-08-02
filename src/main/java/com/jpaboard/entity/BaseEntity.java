package com.jpaboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_by")
    String createdAt;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    String createdBy;

    public void changeCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void changeCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
