package spring.jpa.board.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "created_by")
  private Long createdBy;

  @PrePersist
  public void prePersist() {
    this.createdAt = this.createdAt.truncatedTo(ChronoUnit.MILLIS);
  }

}
