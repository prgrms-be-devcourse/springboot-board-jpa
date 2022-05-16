package com.prgrms.hyuk.domain.user;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_NAME_FORMAT_EXP_MSG;

import com.prgrms.hyuk.exception.InvalidNameFormatException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private static final int MAX = 30;
    private static final int MIN = 1;

    @Column(nullable = false, length = 30)
    private String name;

    protected Name() {
    }

    public Name(String name) {
        validateNameFormat(name);

        this.name = name;
    }

    private void validateNameFormat(String name) {
        if (name.length() < MIN || name.length() > MAX) {
            throw new InvalidNameFormatException(INVALID_NAME_FORMAT_EXP_MSG);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name1 = (Name) o;
        return name.equals(name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
