package com.pppp0722.boardjpa.domain.post;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "created_by")
    private LocalDateTime createdAt;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private String createdBy;
}
