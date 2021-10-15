package com.kdt.Board.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = this.createdAt.truncatedTo(ChronoUnit.SECONDS);
        this.modifiedAt = this.modifiedAt.truncatedTo(ChronoUnit.SECONDS);
    }
}
