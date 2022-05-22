package com.prgrms.springbootboardjpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class DatetimeEntity {
    private String createdBy;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}
