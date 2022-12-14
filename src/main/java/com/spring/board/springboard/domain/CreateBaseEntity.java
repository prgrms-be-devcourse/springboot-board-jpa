package com.spring.board.springboard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class CreateBaseEntity {

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

//    @Column(name = "created_by")
//    private User createdBy;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

//    public User getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(User createdBy) {
//        this.createdBy = createdBy;
//    }
}
