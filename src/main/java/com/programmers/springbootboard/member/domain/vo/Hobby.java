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
@EqualsAndHashCode(of = "hobby")
public class Hobby {
    @Transient
    private static final String HOBBY_VALIDATOR = "^.{1,50}$";

    @Column(name = "member_hobby", nullable = false)
    private String hobby;

    public Hobby(String hobby) {
        validate(hobby);
        this.hobby = hobby;
    }

    private void validate(String name) {
        if (!Pattern.matches(HOBBY_VALIDATOR, name)) {
            throw new InvalidArgumentException(ErrorMessage.INVALID_MEMBER_HOBBY);
        }
    }
}
