package com.kdt.programmers.forum.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class CreationBaseEntity {
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "created_by")
    private String createdBy;
}
