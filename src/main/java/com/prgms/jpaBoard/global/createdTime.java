package com.prgms.jpaBoard.global;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class createdTime {

    @CreatedDate
    private ZonedDateTime createdAt;

    protected createdTime() {

    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
