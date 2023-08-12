package org.prgms.boardservice.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import static org.prgms.boardservice.util.ErrorMessage.INVALID_USER_PASSWORD_PATTERN;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Password {

    private static final String PASSWORD_REGEX = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";

    @Column(length = 100, nullable = false)
    private String password;

    public Password(String password) {
        validatePasswordPattern(password);
        this.password  = password;
    }

    private void validatePasswordPattern(String password) {
        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            throw new IllegalArgumentException(INVALID_USER_PASSWORD_PATTERN.getMessage());
        }
    }
}
