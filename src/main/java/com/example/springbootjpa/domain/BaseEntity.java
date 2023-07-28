package com.example.springbootjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Column(name = "created_by")
    @Setter
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private ZonedDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
