package com.eden6187.jpaboard.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "created_at", columnDefinition = "TIMESTAMP")
  private LocalDateTime cratedAt;
}
