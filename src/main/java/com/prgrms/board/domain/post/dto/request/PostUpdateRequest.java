package com.prgrms.board.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 30, message = "제목은 최대 30자까지 가능합니다.")
    String title,

    @NotBlank(message = "내용은 필수입니다.")
    String content
) {
}
