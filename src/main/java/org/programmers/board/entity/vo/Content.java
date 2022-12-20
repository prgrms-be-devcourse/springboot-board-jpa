package org.programmers.board.entity.vo;

import org.programmers.board.exception.EmptyContentException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

@Embeddable
public class Content {

    @Lob
    @Column(nullable = false)
    private String content;

    protected Content() {

    }

    public Content(String content) {
        validateContent(content);
        this.content = content;
    }

    private void validateContent(String content) {
        if (content.isBlank()) {
            throw new EmptyContentException("내용을 입력해주세요.");
        }
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Content)) return false;
        Content content1 = (Content) o;
        return Objects.equals(getContent(), content1.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent());
    }
}