package org.jpa.kdtboard.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by yunyun on 2021/10/11.
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_by",  nullable = false)
    private String createdBy;


    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}
