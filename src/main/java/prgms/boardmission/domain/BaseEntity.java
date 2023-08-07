package prgms.boardmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedBy
    private String createdBy;
    @CreatedDate
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime cratedAt;

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCratedAt() {
        return cratedAt;
    }

    public void addCratedAt() {
        this.cratedAt = LocalDateTime.now();
    }
}
