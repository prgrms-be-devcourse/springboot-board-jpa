package com.devcourse.bbs.controller.bind;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostCreateRequest {
    @NotBlank(message = "Title text cannot be blank.")
    private String title;
    @NotBlank(message = "Content text cannot be blank.")
    private String content;
    @NotBlank(message = "Username text cannot be blank.")
    private String user;
}
