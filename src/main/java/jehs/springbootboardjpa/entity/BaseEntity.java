package jehs.springbootboardjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Builder.Default
    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    @Column(name = "updated_at", columnDefinition = "datetime")
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Column(name = "deleted_at", columnDefinition = "datetime")
    private LocalDateTime deletedAt;

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
