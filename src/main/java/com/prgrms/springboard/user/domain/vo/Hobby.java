package com.prgrms.springboard.user.domain.vo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

import lombok.Getter;

@Getter
@Embeddable
public class Hobby {

    private static final String HOBBY_NOT_BE_NULL_AND_BLANK_MESSAGE = "취미는 null 이거나 공백만 있을 수 없습니다.";
    private static final String HOBBY_OVER_MAX_LENGTH = "취미는 글자수가 15를 초과할 수 없습니다.";
    private static final int HOBBY_MAX_LENGTH = 15;

    @Column(name = "hobby", length = 15, nullable = false)
    private String value;

    protected Hobby() {
    }

    public Hobby(String value) {
        this.value = value;
        validateHobby(value);
    }

    private void validateHobby(String hobby) {
        if (Objects.isNull(hobby) || hobby.isBlank()) {
            throw new InvalidValueException(HOBBY_NOT_BE_NULL_AND_BLANK_MESSAGE);
        }

        if (hobby.length() > HOBBY_MAX_LENGTH) {
            throw new InvalidValueException(HOBBY_OVER_MAX_LENGTH);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Hobby hobby1 = (Hobby)o;
        return this.getValue().equals(hobby1.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValue());
    }

}
