package com.misson.jpa_board.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
@NoArgsConstructor
public class Hobby {
    @Size(min = 1, max = 30)
    @Column(name = "user_hobby")
    private String hobby;

    public Hobby(String hobby) {
        this.hobby = hobby;
    }
}

