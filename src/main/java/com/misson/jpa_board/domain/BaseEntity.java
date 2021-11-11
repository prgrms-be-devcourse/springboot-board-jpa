package com.misson.jpa_board.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    @CreatedBy
    @Column(name = "created_by")
    private Long userId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
