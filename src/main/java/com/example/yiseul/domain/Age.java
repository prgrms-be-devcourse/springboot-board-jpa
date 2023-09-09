package com.example.yiseul.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Age {

    public static final int MIN_AGE = 1;

    @Column(nullable = false)
    private Integer age;

    public Age(Integer age) {
        validationAge(age);
        this.age = age;
    }

    private void validationAge(Integer age) {
        if (age < MIN_AGE || age == null) {

            throw new IllegalArgumentException("나이는 0세 이상으로 입력되어야만 합니다.");
        }
    }


}
