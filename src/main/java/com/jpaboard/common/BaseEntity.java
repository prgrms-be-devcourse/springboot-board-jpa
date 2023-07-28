package com.jpaboard.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Column(name = "create_at", nullable = false, updatable = false)
    @CreatedDate
    LocalDateTime createAt;
    @Column(name = "update_at", nullable = false)
    @LastModifiedDate
    LocalDateTime updateAt;
}
