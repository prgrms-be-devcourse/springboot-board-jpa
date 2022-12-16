package com.prgrms.boardjpa.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {
    private LocalDateTime createdAt;
    private String createdBy;
}
