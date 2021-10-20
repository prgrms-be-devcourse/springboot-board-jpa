package com.misson.jpa_board.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class Hobby {

    @Column(name = "user_hobby")
    private String hobby;

    public Hobby(String hobby) {
        if (!validate(hobby)) {
            throw new IllegalArgumentException("취미은 1글자 이상 30글자 이하여야 합니다.");
        }
        this.hobby = hobby;
    }

    private boolean validate(String hobby) {
        return hobby.length() > 0 && hobby.length() <= 30;
    }
}

