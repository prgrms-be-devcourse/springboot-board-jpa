package com.programmers.springbootboardjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    protected String createdBy;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
