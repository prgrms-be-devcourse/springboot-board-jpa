package com.prgms.jpaBoard.global;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class createdTime {

    @CreatedDate
    private LocalDateTime createdAt;

    protected createdTime() {

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
