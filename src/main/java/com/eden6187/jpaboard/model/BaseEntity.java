package com.eden6187.jpaboard.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BaseEntity {

  @Column(name = "created_by")
  protected String createdBy;

  @Column(name = "created_at", columnDefinition = "TIMESTAMP")
  protected LocalDateTime createdAt;

}
