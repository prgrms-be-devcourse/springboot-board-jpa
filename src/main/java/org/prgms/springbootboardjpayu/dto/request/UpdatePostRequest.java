package org.prgms.springbootboardjpayu.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdatePostRequest(
        @NotBlank
        @Length(min = 1, max = 30)
        String title,
        String content
) {
}
