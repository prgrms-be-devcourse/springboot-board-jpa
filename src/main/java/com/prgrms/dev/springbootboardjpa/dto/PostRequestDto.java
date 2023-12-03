package com.prgrms.dev.springbootboardjpa.dto;

import lombok.Builder;

@Builder
public record PostRequestDto(String title, String content) {
    // 검증
    // 생성, 업데이트 를 분리?
    // 요구사항이 늘었을 때를 생각해보자
}
