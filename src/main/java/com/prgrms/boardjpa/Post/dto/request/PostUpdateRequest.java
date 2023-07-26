package com.prgrms.boardjpa.Post.dto.request;

import javax.validation.constraints.NotBlank;

public record PostUpdateRequest(@NotBlank(message = "변경할 제목을 입력해주세요.") String title,
                                @NotBlank(message = "변경할 내용을 입력해주세요.") String content) {
}