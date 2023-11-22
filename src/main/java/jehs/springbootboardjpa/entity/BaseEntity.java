package jehs.springbootboardjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDateTime createdAt;
    @Column(name = "updated_at", columnDefinition = "datetime")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at", columnDefinition = "datetime")
    private LocalDateTime deletedAt;
}
