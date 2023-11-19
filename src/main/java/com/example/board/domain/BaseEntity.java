package com.example.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    private String createdBy;

}
