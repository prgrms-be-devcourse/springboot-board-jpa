package com.example.springbootboard.dto;

import com.example.springbootboard.entity.Post;
import com.example.springbootboard.entity.Title;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PostRequestDto {
    @NotNull(message = "사용자 정보가 없습니다.")
    private Long userId;
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "게시판 내용은 필수입니다.")
    private String content;
}
