package com.prgrms.jpaboard.global.common.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {
    @Column(name="created_by", nullable = false)
    private String createdBy;

    @Column(name="created_at", columnDefinition = "TIMESTAMP", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", columnDefinition = "TIMESTAMP", nullable = false)
    protected LocalDateTime updatedAt;

    public BaseEntity() {
    }

    public BaseEntity(String createdBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
