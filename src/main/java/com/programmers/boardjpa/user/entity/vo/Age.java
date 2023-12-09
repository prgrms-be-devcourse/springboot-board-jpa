package com.programmers.boardjpa.user.entity.vo;

import com.programmers.boardjpa.user.exception.UserErrorCode;
import com.programmers.boardjpa.user.exception.UserException;
import jakarta.persistence.Embeddable;

@Embeddable
public class Age {
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 110;

    private final int age;

    public Age(int age) {
        validateAge(age);

        this.age = age;
    }

    protected Age() {
        this.age = -1;
    }

    private void validateAge(int age) {
        if (age <= MIN_AGE || age >= MAX_AGE) {
            throw new UserException(UserErrorCode.INVALID_AGE_RANGE);
        }
    }
}
