package com.programmers.springbootboard.member.domain.vo;

import com.programmers.springbootboard.error.ErrorMessage;
import com.programmers.springbootboard.error.exception.InvalidArgumentException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "age")
public class Age {
    @Transient
    private static final String AGE_VALIDATOR = "^100|[1-9]?\\d$";

    @Column(name = "member_age", nullable = false)
    private Integer age;

    public Age(String age) {
        validate(age);
        this.age = parsingAge(age);
    }

    private void validate(String age) {
        if (!Pattern.matches(AGE_VALIDATOR, age)) {
            throw new InvalidArgumentException(ErrorMessage.INVALID_MEMBER_AGE);
        }
    }

    private int parsingAge(String age) {
        return Integer.parseInt(age);
    }
    
    @Override
    public String toString() {
        return String.valueOf(age);
    }
}

