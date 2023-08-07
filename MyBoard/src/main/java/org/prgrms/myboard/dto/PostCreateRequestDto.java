package org.prgrms.myboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static org.prgrms.myboard.util.ErrorMessage.*;

public record PostCreateRequestDto(
    @NotBlank(message = TITLE_NOT_BLANK_MESSAGE)
    String title,
    @NotBlank(message = CONTENT_NOT_BLANK_MESSAGE)
    String content,
    @NotNull(message = ID_NOT_NULL_MESSAGE)
    Long userId
) {
}
