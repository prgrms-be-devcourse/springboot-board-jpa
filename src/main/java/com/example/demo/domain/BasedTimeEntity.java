package com.example.demo.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BasedTimeEntity {

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    protected void addCreator(String createdBy) {
        this.createdBy = createdBy;
        createdAt = LocalDateTime.now();
    }

    protected void changeUpdatedTime() {
        this.updatedAt = LocalDateTime.now();
    }
}
