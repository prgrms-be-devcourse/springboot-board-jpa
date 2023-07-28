package com.prgms.jpaBoard.global;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    private ZonedDateTime createdAt;

    @CreatedBy
    private String createdBy;

    protected BaseEntity() {

    }

    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void setCreatedBy(String name) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
