package com.kdt.bulletinboard.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime cratedAt;
    @Column(name = "created_by")
    private String createdBy;
}
