package com.example.demo.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BasedTimeEntity {

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected void addCreator(String createdBy) {
        this.createdBy = createdBy;
    }
}
