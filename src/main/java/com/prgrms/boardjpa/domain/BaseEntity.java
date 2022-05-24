package com.prgrms.boardjpa.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
