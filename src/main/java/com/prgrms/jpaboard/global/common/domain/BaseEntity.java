package com.prgrms.jpaboard.global.common.domain;

import com.prgrms.jpaboard.global.util.Validator;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static com.prgrms.jpaboard.global.util.Validator.*;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity {
    @Column(name="created_by", nullable = false)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    protected LocalDateTime updatedAt;

    protected BaseEntity() {
    }

    public BaseEntity(String className, String createdBy) {
        validateCreatedBy(className, createdBy);
        this.createdBy = createdBy;
    }
}
