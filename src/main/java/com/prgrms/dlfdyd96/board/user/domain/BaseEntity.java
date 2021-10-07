package com.prgrms.dlfdyd96.board.user.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
// TODO: updatedAt Entity Listener 추가 (p.133)
abstract class BaseEntity {
  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_at", columnDefinition = "TIMESTAMP")
  private LocalDateTime createdAt;
}
