package org.prgms.boardservice.domain.post;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_CONTENT;
import static org.springframework.util.StringUtils.hasText;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Content {

    @Lob
    @NotNull
    private String value;

    public Content(String value) {
        validateContentLength(value);
        this.value = value;
    }

    private void validateContentLength(String value) {
        if (!hasText(value) || value.length() > 500) {
            throw new IllegalArgumentException(INVALID_POST_CONTENT.getMessage());
        }
    }
}
