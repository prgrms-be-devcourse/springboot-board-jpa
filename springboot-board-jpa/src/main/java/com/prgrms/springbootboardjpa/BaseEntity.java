package com.prgrms.springbootboardjpa;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Column
    private String createdBy;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

}
