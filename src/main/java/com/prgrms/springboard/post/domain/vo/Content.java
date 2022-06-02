package com.prgrms.springboard.post.domain.vo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

import lombok.Getter;

@Getter
@Embeddable
public class Content {

    private static final String CONTENT_NOT_BE_NULL_AND_BLANK_MESSAGE = "내용은 null 이거나 공백만 있을 수 없습니다.";
    private static final String CONTENT_OVER_MAX_LENGTH = "내용은 글자수가 200을 초과할 수 없습니다.";
    private static final int CONTENT_MAX_LENGTH = 200;

    @Column(length = 200, nullable = false)
    private String content;

    protected Content() {
    }

    public Content(String content) {
        validateContent(content);
        this.content = content;
    }

    private void validateContent(String content) {
        if (Objects.isNull(content) || content.isBlank()) {
            throw new InvalidValueException(CONTENT_NOT_BE_NULL_AND_BLANK_MESSAGE);
        }

        if (content.length() > CONTENT_MAX_LENGTH) {
            throw new InvalidValueException(CONTENT_OVER_MAX_LENGTH);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Content content1 = (Content)o;
        return getContent().equals(content1.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent());
    }
}
