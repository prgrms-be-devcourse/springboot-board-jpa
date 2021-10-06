package com.example.springbootboard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Title {

    public static final int TITLE_MAX_LENGTH = 40;
    public static final int TITLE_MIN_LENGTH = 1;

    @Column(name = "title", nullable = false, length = TITLE_MAX_LENGTH)
    private String title;

    public Title(String title) {
        this.title = title;
    }

    public boolean isShorterThanMinLength() {
        return length() < TITLE_MIN_LENGTH;
    }

    public boolean isLongerThanMaxLength() {
        return length() > TITLE_MAX_LENGTH;
    }

    public int length() {
        return title.length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title1 = (Title) o;
        return Objects.equals(getTitle(), title1.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
