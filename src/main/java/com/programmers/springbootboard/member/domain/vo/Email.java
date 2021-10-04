package com.programmers.springbootboard.member.domain.vo;

import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.InvalidArgumentException;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class Email {
    @Transient
    private static final String EMAIL_VALIDATOR = "^[_a-zA-Z0-9-\\+]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,3})$";

    @Column(name = "member_email", nullable = false)
    private String email;

    protected Email() {

    }

    public Email(String email) {
        validate(email);
        this.email = email;
    }

    public void validate(String email) {
        if (!Pattern.matches(EMAIL_VALIDATOR, email)) {
            throw new InvalidArgumentException(ErrorMessage.INVALID_MEMBER_EMAIL);
        }
    }

    public String getEmail() {
        return email;
    }
}
