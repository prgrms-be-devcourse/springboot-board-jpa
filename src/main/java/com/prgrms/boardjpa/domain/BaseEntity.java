package com.prgrms.boardjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Column(name="created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private String createdBy;

    public BaseEntity(){}
    public BaseEntity(LocalDateTime createdAt, String createdBy) {
    }
}
