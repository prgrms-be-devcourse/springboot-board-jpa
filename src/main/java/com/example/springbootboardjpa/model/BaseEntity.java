package com.example.springbootboardjpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Column(name = "created_by",updatable = false)
    @Setter
    private String createdBy;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
