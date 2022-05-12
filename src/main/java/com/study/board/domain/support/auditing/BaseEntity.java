package com.study.board.domain.support.auditing;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

}
