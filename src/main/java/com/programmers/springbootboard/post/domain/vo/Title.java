package com.programmers.springbootboard.post.domain.vo;

import com.programmers.springbootboard.error.ErrorMessage;
import com.programmers.springbootboard.error.exception.InvalidArgumentException;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
public class Title {
    @Transient
    private static final String TITLE_REGEX = "^.{1,25}$";

    @Column(name = "post_title")
    private String title;

    protected Title() {
    }

    public Title(String title) {
        validate(title);
        this.title = title;
    }

    public void validate(String title) {
        if (!Pattern.matches(TITLE_REGEX, title)) {
            throw new InvalidArgumentException(ErrorMessage.INVALID_POST_TITLE);
        }
    }

    public String getTitle() {
        return title;
    }
}
