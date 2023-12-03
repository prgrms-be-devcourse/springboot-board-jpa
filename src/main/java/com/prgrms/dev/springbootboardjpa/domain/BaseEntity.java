package com.prgrms.dev.springbootboardjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 테이블에 없는 상속 구조를 표현할 수 있는, abstract class로는 구현 가능한지 고민 ...
@EntityListeners(AuditingEntityListener.class) // 변경 감지
public class BaseEntity {
    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createdAt;

    // updatedAt 추가 ...
}
