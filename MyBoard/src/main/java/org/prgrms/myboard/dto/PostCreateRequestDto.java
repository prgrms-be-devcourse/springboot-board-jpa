package org.prgrms.myboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostCreateRequestDto(
    @NotBlank(message = "제목이 비어있으면 안됩니다.")
    String title,
    @NotBlank(message = "내용이 비어있으면 안됩니다.")
    String content,
    @NotNull(message = "유저Id가 null이면 안됩니다.")
    Long userId
) {
}
