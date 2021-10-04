package com.programmers.springbootboard.post.domain.vo;

import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.InvalidArgumentException;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class Content {
    @Transient
    private static final String CONTENT_REGEX = "^.{5,2000}$";

    @Column(name = "post_content")
    private String content;

    protected Content() {
    }

    public Content(String content) {
        validate(content);
        this.content = content;
    }

    public void validate(String content) {
        if (!Pattern.matches(CONTENT_REGEX, content)) {
            throw new InvalidArgumentException(ErrorMessage.INVALID_POST_CONTENT);
        }
    }

    public String getContent() {
        return content;
    }
}