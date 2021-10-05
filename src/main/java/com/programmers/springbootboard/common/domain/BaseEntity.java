package com.programmers.springbootboard.common.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity<U> {
    @CreatedBy
    @Column(name = "created_by")
    private U createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private U lastModifiedBy;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime modifiedDate;

    public void addByInformation(U id) {
        this.createdBy = id;
        this.lastModifiedBy = id;
    }

    public void lastModifiedId(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
