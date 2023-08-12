package com.example.jpaboard.post.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostSaveApiRequest(@NotNull(message = "memberId 값이 입력되지 않았습니다.") Long memberId,
                                 @NotBlank(message = "title 값이 입력되지 않았습니다.") String title,
                                 @NotBlank(message = "content 값이 입력되지 않았습니다.") String content) { }
