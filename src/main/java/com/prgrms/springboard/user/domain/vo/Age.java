package com.prgrms.springboard.user.domain.vo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

import lombok.Getter;

@Getter
@Embeddable
public class Age {

    private static final String AGE_NOT_BE_NEGATIVE_MESSAGE = "나이는 음수가 될 수 없습니다.";
    private static final int AGE_MIN_VALUE = 0;

    @Column(name = "age", nullable = false)
    private int value;

    protected Age() {
    }

    public Age(int value) {
        validateAge(value);
        this.value = value;
    }

    private void validateAge(int age) {
        if (age < AGE_MIN_VALUE) {
            throw new InvalidValueException(AGE_NOT_BE_NEGATIVE_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Age age1 = (Age)o;
        return this.getValue() == age1.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValue());
    }
}
