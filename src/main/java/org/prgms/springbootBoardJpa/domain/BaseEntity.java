package org.prgms.springbootBoardJpa.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.MappedSuperclass;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    private ZonedDateTime createdAt;

    @CreatedBy
    private String createdBy;

    public void updateCreatedAt() {
        this.createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void updateCreatedBy(String name) {
        this.createdBy = name;
    }

}
