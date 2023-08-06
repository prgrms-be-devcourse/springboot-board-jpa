package com.example.jpaboard.post.controller.dto;

import jakarta.validation.constraints.NotNull;

public record SaveApiRequest(@NotNull(message = "memberId 값이 입력되지 않았습니다.") Long memberId,
                             @NotNull(message = "title 값이 입력되지 않았습니다.") String title,
                             @NotNull(message = "content 값이 입력되지 않았습니다.") String content) { }
