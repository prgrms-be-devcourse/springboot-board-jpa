package com.programmers.jpaboard.member.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {

    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 20;

    @Column(name = "name", length = MAX_LENGTH)
    private String name;

    public Name(String name) {
        this.name = name;
    }
}
