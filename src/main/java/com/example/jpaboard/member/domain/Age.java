package com.example.jpaboard.member.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Age {

    private static final int AGE_MIN = 0;

    private int age;

    protected Age() {
    }

    public Age(int age) {
        validateAge(age);
        this.age = age;
    }

    private void validateAge(int age) {
        if (age < AGE_MIN) {
            throw new IllegalArgumentException();
        }
    }

}
