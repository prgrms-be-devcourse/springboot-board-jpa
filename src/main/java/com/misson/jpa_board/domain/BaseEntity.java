package com.misson.jpa_board.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_by")
    private Long userId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void setInfo(Long id) {
        userId = id;
        createdAt = LocalDateTime.now();
    }
}
