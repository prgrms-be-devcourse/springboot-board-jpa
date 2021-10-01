package com.programmers.jpaboard;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class DateEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    private String createBy;
}
