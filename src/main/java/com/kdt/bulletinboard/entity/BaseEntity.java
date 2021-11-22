package com.kdt.bulletinboard.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    public void setCreatedAt(LocalDateTime cratedAt) {
        this.createdAt = cratedAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
