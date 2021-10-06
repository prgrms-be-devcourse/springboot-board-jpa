package com.programmers.jpaboard.member.domain.vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "hobby")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hobby {

    @Id
    @GeneratedValue()
    private Long id;

    private String hobby;

    public Hobby(String hobby) {
        this.hobby = hobby;
    }

    public String getHobby(){
        return hobby;
    }
}
