package com.prgrms.hyuk.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    protected BaseEntity() {
    }
    
    public BaseEntity(String createdBy) {
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }
}
