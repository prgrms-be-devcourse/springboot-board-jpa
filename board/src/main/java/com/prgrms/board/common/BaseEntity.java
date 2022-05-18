package com.prgrms.board.common;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {

    private LocalDateTime createdAt = LocalDateTime.now();
    private String createdBy;

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}