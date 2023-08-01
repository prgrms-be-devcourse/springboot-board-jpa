package com.example.yiseul.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Age {

    public static final int MIN_AGE = 0;

    private Integer age;

    private Age(Integer age) {
        this.age = age;
    }

    public static Age from(Integer age) {
        validationPositiveAge(age);
        return new Age(age);
    }

    private static void validationPositiveAge(Integer age) {
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("나이는 0세 이상이어야 합니다.");
        }
    }

    public void changeAge(Integer updateAge) {
        if (updateAge != null) {
            validationPositiveAge(updateAge);
            this.age = updateAge;
        }
    }
}
