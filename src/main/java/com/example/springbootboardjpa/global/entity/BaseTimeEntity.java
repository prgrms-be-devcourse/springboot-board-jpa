package com.example.springbootboardjpa.global.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseTimeEntity {

    @CreatedDate
    private String createdAt;

    private long createdBy;

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    @PrePersist
    void onPrePersist(){
        this.createdAt = LocalDateTime
                .now()
                .format(
                        DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")
                );
    }
}
