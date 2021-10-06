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

    @Column(name = "name", length = 20)
    private String name;

    public Name(String name) {
        this.name = name;
    }
}
