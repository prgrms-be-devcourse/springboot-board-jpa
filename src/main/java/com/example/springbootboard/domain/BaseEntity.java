package com.example.springbootboard.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class BaseEntity {

    private String createdBy;
    private LocalDateTime createdAt;

    public BaseEntity(String createdBy, LocalDateTime createdAt) {

        Assert.notNull(createdBy, "CreateBy should not be null");

        Assert.notNull(createdAt, "CreatedAt should not be null");

        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
}
