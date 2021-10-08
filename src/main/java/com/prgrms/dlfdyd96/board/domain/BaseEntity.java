package com.prgrms.dlfdyd96.board.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;

@Getter
@MappedSuperclass
// TODO: updatedAt Entity Listener 추가 (p.133)
abstract class BaseEntity {
  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_at", columnDefinition = "TIMESTAMP")
  private LocalDateTime createdAt;
}
