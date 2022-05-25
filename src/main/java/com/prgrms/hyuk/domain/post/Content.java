package com.prgrms.hyuk.domain.post;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_CONTENT_FORMAT_EXP_MSG;

import com.prgrms.hyuk.exception.InvalidContentFormatException;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Content {

    private static final int MIN = 0;

    @Lob
    private String content;

    protected Content() {
    }

    public Content(String content) {
        validateContentLength(content);

        this.content = content;
    }

    private void validateContentLength(String content) {
        if (content.length() == MIN) {
            throw new InvalidContentFormatException(INVALID_CONTENT_FORMAT_EXP_MSG);
        }
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Content content1 = (Content) o;
        return getContent().equals(content1.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent());
    }
}
