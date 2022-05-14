package org.prgrms.kdt.domain;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @CreatedBy
  @Size(min = 2, max = 20)
  private String createdBy;
}