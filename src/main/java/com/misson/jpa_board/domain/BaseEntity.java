package com.misson.jpa_board.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_by")
    Long createdBy;
    @Column(name = "created_at")
    LocalDateTime createdAt;

    public void setInfo(Long id) {
        createdBy = id;
        createdAt = LocalDateTime.now();
    }
}
