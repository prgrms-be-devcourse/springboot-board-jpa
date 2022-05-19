package com.prgrms.springboard.user.domain.vo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

import lombok.Getter;

@Getter
@Embeddable
public class Name {

    private static final String NAME_NOT_BE_NULL_AND_BLANK_MESSAGE = "이름은 null 이거나 공백만 있을 수 없습니다.";
    private static final String NAME_OVER_MAX_LENGTH = "이름은 글자수가 10를 초과할 수 없습니다.";
    private static final int NAME_MAX_LENGTH = 10;

    @Column(name = "name", length = 10, nullable = false)
    private String value;

    protected Name() {
    }

    public Name(String value) {
        this.value = value;
        validateName(value);
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new InvalidValueException(NAME_NOT_BE_NULL_AND_BLANK_MESSAGE);
        }

        if (name.length() > NAME_MAX_LENGTH) {
            throw new InvalidValueException(NAME_OVER_MAX_LENGTH);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Name name1 = (Name)o;
        return this.getValue().equals(name1.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValue());
    }
}
