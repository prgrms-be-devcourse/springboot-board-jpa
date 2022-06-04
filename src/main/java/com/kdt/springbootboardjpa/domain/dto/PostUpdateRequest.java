package com.kdt.springbootboardjpa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequest {
    @NotBlank(message = "제목은 비워둘 수 없습니다.")
    private String title;
    private String content;
}
