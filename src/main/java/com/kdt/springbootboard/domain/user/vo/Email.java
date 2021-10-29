package com.kdt.springbootboard.domain.user.vo;

import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Email {

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    public Email(String email) {
        if (!validate(email)) throw new IllegalArgumentException("Invalid Email address");
        this.email = email;
    }

    public static boolean validate(String email) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", email);
    }

    public String getEmail() {
        return email;
    }
}
