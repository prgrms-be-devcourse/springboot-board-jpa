package org.programmers.board.entity.vo;

import org.programmers.board.exception.TooLongNameException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    private static final int MAX_NAME_LENGTH = 15;

    @Column(nullable = false, length = 15)
    private String name;

    protected Name() {
    }

    public Name(String name) {
        validateUserName(name);
        this.name = name;
    }

    private void validateUserName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new TooLongNameException("이름이 너무 깁니다. 15자 이하로 해주세요.");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name userName = (Name) o;
        return Objects.equals(name, userName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}