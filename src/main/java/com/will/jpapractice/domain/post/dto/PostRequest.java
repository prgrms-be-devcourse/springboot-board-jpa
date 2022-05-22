package com.will.jpapractice.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class PostRequest {
    @NotBlank
    @Size(max = 50)
    private String title;

    private String content;

}
