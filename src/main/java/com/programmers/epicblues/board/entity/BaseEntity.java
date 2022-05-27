package com.programmers.epicblues.board.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

  protected String createdBy;
  @Column(updatable = false)
  protected LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    createdAt = LocalDateTime.now();
  }
}
