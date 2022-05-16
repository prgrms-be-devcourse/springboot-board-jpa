package com.prgrms.hyuk.domain.post;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_TITLE_FORMAT_EXP_MSG;

import com.prgrms.hyuk.exception.InvalidTitleFormatException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Title {

    private static final int MIN = 10;
    private static final int MAX = 50;

    @Column(nullable = false, length = 50)
    private String title;

    protected Title() {
    }

    public Title(String title) {
        validateTitleFormat(title);

        this.title = title;
    }

    private void validateTitleFormat(String title) {
        if (title.length() < MIN || title.length() > MAX) {
            throw new InvalidTitleFormatException(INVALID_TITLE_FORMAT_EXP_MSG);
        }
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Title title1 = (Title) o;
        return title.equals(title1.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
