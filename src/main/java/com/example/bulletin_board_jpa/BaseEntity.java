package com.example.bulletin_board_jpa;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column (name = "created_at")
    private String createdAt;

    @Column (name = "created_by")
    private LocalDateTime createdBy;

}
