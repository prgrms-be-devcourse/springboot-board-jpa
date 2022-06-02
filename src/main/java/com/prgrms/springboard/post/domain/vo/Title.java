package com.prgrms.springboard.post.domain.vo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

import lombok.Getter;

@Getter
@Embeddable
public class Title {

    private static final String TITLE_NOT_BE_NULL_AND_BLANK_MESSAGE = "재목은 null 이거나 공백만 있을 수 없습니다.";
    private static final String TITLE_OVER_MAX_LENGTH = "제목은 글자수가 30을 초과할 수 없습니다.";
    private static final int TITLE_MAX_LENGTH = 30;

    @Column(length = 30, nullable = false)
    private String title;

    protected Title() {
    }

    public Title(String title) {
        validateTitle(title);
        this.title = title;
    }

    private void validateTitle(String title) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new InvalidValueException(TITLE_NOT_BE_NULL_AND_BLANK_MESSAGE);
        }

        if (title.length() > TITLE_MAX_LENGTH) {
            throw new InvalidValueException(TITLE_OVER_MAX_LENGTH);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Title title1 = (Title)o;
        return getTitle().equals(title1.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
