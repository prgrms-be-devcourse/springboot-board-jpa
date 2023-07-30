package org.prgrms.myboard.dto;

import jakarta.annotation.Nullable;

public record PostCursorRequestDto(
    @Nullable
    Long cursorId,
    Integer pageSize
) {
}
