package com.homework.springbootboard.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at",columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}
