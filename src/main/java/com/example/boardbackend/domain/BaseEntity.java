package com.example.boardbackend.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // 자식 클래스에게 필드 넘겨주기 위함
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
    // JPA Audit 사용
    @CreatedDate
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}
