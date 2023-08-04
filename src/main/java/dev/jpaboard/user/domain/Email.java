package dev.jpaboard.user.domain;

import dev.jpaboard.user.exception.InvalidEmailException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");

    private String email;

    public Email(String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(String userEmail) throws InvalidEmailException {
        if (!EMAIL_PATTERN.matcher(userEmail).matches()) {
            throw new InvalidEmailException();
        }
    }

}

