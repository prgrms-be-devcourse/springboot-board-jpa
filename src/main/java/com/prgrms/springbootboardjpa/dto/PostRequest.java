package com.prgrms.springbootboardjpa.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {
    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String title;

    @NotBlank(message = "본문 내용은 공백일 수 없습니다.")
    private String content;

    @Setter private Object user;
}
