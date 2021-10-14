package com.prgrms.board.dto.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record PostCreateRequest(
        @Positive Long userId,
        @NotBlank(message = "제목이 비어있습니다.") String title,
        String content) {
}
