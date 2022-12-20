package org.programmers.board.entity.vo;

import org.programmers.board.exception.EmptyTitleException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Title {

    @Column(nullable = false, length = 30)
    private String title;

    protected Title() {

    }

    public Title(String title) {
        validateTitle(title);
        this.title = title;
    }

    private void validateTitle(String title) {
        if (title.isBlank()) {
            throw new EmptyTitleException("제목을 입력해주세요.");
        }
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Title)) return false;
        Title title1 = (Title) o;
        return Objects.equals(getTitle(), title1.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}