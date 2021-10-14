package com.prgrms.board.dto.post;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record PostCreateRequest(
        @Positive Long userId,
        @NotBlank(message = "제목이 비어있습니다.") @Length(max = 250) String title,
        String content) {
}
