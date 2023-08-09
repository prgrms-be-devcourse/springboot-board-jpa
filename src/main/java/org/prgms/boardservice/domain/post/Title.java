package org.prgms.boardservice.domain.post;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_TITLE;
import static org.springframework.util.StringUtils.hasText;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Title {

    @Column(length = 20)
    @NotNull
    private String value;

    public Title(String value) {
        validateTitleLength(value);
        this.value = value;
    }

    private void validateTitleLength(String value) {
        if (!hasText(value) || value.length() > 20) {
            throw new IllegalArgumentException(INVALID_POST_TITLE.getMessage());
        }
    }
}
