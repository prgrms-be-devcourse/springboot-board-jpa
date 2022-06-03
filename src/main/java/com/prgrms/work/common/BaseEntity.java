package com.prgrms.work.common;

import java.time.LocalDateTime;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

@MappedSuperclass
public class BaseEntity {

    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;

    protected BaseEntity() {}

    public BaseEntity(String createdBy, LocalDateTime createdAt) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
