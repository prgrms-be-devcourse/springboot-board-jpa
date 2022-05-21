package com.study.board.domain.base;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class BaseIdEntity {

    @Id
    @GeneratedValue
    private Long id;

}
