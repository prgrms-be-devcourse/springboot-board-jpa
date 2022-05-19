package com.example.springboardjpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@MappedSuperclass
public class BaseEntityCreate {
    @Column(name = "created_by")
    private String createBy;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    public BaseEntityCreate(String createBy, LocalDateTime createdAt) {
        this.createBy = createBy;
        this.createdAt = createdAt;
    }
}
