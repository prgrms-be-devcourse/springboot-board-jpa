package com.programmers.jpaboard.member.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Age {

    public static final int MIN_AGE = 10;
    public static final int MAX_AGE = 100;

    private int age;

    public Age(int age) {
        this.age = age;
    }
}
