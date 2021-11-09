package com.devcourse.bbs.controller.bind;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostUpdateRequest {
    @NotBlank(message = "Updated title text cannot be blank.")
    private String title;
    @NotBlank(message = "Updated content text cannot be blank.")
    private String content;
}
