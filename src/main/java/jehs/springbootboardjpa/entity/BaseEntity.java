package jehs.springbootboardjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Builder.Default
    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "datetime")
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Column(name = "deleted_at", columnDefinition = "datetime")
    private LocalDateTime deletedAt;
}
