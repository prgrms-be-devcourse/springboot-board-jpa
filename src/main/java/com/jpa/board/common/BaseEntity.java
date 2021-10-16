package com.jpa.board.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity <T>{

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(nullable = false, length = 20, updatable = false)
    private T createdBy;

    protected void addCreatedBy(T t){
        this.createdBy = t;
    }

}
