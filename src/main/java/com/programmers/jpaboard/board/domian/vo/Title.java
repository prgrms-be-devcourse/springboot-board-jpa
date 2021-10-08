package com.programmers.jpaboard.board.domian.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Title {

    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 100;

    @Column(length = MAX_LENGTH)
    private String title;

    public Title(String title) {
        this.title = title;
    }
}
