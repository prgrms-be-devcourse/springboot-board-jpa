package dev.jpaboard.user.domain;

import dev.jpaboard.user.exception.InvalidPasswordException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");

    private String password;

    public Password(String password) throws InvalidPasswordException {
        validatePassword(password);
        this.password = password;
    }

    private void validatePassword(String userPassword) throws InvalidPasswordException {
        if (!PASSWORD_REGEX.matcher(userPassword).matches()) {
            throw new InvalidPasswordException();
        }
    }

}
