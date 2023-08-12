package org.prgms.boardservice.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import static org.prgms.boardservice.util.ErrorMessage.INVALID_USER_EMAIL_PATTERN;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Email {
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Column(length = 20, unique = true)
    private String email;

    public Email(String email) {
        validateEmailPattern(email);
        this.email = email;
    }

    private void validateEmailPattern(String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException(INVALID_USER_EMAIL_PATTERN.getMessage());
        }
    }
}
