package com.prgrms.board.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class Base {

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created_at;

}
