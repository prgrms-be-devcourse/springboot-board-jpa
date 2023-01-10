package com.prgrms.be.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class PostCreateRequest {
    @NotBlank(message = "게시글의 제목을 기입해야 합니다.")
    @Size(min = 1, max = 20)
    private String title;

    @NotBlank(message = "게시글의 내용을 기입해야 합니다.")
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    private Long userId;
}
