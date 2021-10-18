package com.kdt.springbootboard.domain.user.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Hobby {

    @Column(name = "user_hobby", nullable = false)
    private String hobby;

    public Hobby(String hobby) {
        if (!validate(hobby)) throw new IllegalArgumentException("The number of Hobby characters exceeded the limit.");
        this.hobby = hobby;
    }

    public static boolean validate(String hobby) {
        return hobby.length() > 0 && hobby.length() <= 30;
    }

    public String getHobby() {
        return hobby;
    }
}
