package com.prgrms.boardjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Column(columnDefinition = "TIMESTAMP", name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;
}
