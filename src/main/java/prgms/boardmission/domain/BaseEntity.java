package prgms.boardmission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime cratedAt;

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCratedAt() {
        return cratedAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCratedAt() {
        this.cratedAt = LocalDateTime.now();
    }
}
