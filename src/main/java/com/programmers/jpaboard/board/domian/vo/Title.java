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
    @Column(length = 50)
    private String title;

    public Title(String title) {
        this.title = title;
    }
}
