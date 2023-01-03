package com.example.springbootboardjpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity { // abstract class 고려
    @Column(name = "created_by",updatable = false)
//    @Setter
    private String createdBy; // setter 필요하면 얘만 ?
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
