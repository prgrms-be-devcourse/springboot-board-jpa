package com.prgrms.springbootboardjpa.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {
    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String title;

    @NotBlank(message = "본문 내용은 공백일 수 없습니다.")
    private String content;

    @Setter private Object user;
}
