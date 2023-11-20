package com.programmers.springboard.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(@Size(max = 60)
								String title,
								@NotBlank(message = "내용을 입력해주세요")
								String content) {
}
