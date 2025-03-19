package org.prgms.springbootboardjpayu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreatePostRequest(
        @NotBlank
        @Length(min = 1, max = 30)
        String title,
        String content,
        @NotNull
        Long userId
) {
}
