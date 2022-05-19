package com.example.spring_jpa_post.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ModifyPostRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
