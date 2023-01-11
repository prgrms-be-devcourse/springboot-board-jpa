package com.prgrms.be.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class PostUpdateRequest {
    @NotBlank(message = "게시글의 제목을 빈 상태로 수정할 수 없습니다.")
    @Size(min = 1, max = 20)
    private String title;

    @NotBlank(message = "게시글의 내용을 빈 상태로 수정할 수 없습니다.")
    @Column(columnDefinition = "TEXT")
    private String content;
}
