package com.example.boardbackend.dto;

import com.example.boardbackend.domain.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {
    private Long id;

    @Size(message="제목은 최대 45자까지 입력 가능합니다", max=45)
    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private Long view;

    @JsonProperty("userInfo")
    private UserDto userDto;

    private LocalDateTime createdAt;

}
