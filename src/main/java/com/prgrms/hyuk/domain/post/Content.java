package com.prgrms.hyuk.domain.post;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_CONTENT_FORMAT_EXP_MSG;

import com.prgrms.hyuk.exception.InvalidContentFormatException;
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
}
