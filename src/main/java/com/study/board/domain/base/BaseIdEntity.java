package com.study.board.domain.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
@EqualsAndHashCode
public abstract class BaseIdEntity {

    @Id
    @GeneratedValue
    private Long id;

}
