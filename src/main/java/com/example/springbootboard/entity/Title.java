package com.example.springbootboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Title {

    private static final int TITLE_MAX_LENGTH = 50;
    private static final int TITLE_MIN_LENGTH = 1;
    private static final String titleRegex = "^[가-힣a-zA-Z0-9_]{1,50}$";

    @Column(name = "title", nullable = false, length = TITLE_MAX_LENGTH)
    private String title;

    public Title(String title) {
        validate(title);
        this.title = title;
    }

    private void validate(String title) {
        Assert.notNull(title, "제목은 반드시 있어야합니다.");
        Assert.isTrue(!isShorterThanMinLength(title), "제목이 너무 짧습니다.");
        Assert.isTrue(!isLongerThanMaxLength(title), "제목이 너무 깁니다.");
        Assert.isTrue(isMatchRegex(title), "제목은 영어, 한글, 특수문자(_)만 포함됩니다.");
    }

    private boolean isShorterThanMinLength(String title) {
        return title.length() < TITLE_MIN_LENGTH;
    }

    private boolean isLongerThanMaxLength(String title) {
        return title.length() > TITLE_MAX_LENGTH;
    }

    private boolean isMatchRegex(String title) {
        return Pattern.matches(titleRegex, title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title other = (Title) o;
        return Objects.equals(title, other.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
