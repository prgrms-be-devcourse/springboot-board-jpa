package com.kdt.devboard.Common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseEntity {

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_ay",columnDefinition = "TIMESTAMP")
    private LocalDateTime createAt;

    public BaseEntity(String createBy, LocalDateTime createAt) {
        this.createBy = createBy;
        this.createAt = createAt;
    }

}