package com.devcourse.bbs.controller.bind;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class PostCreateRequest {
    @NotBlank(message = "Title text cannot be blank.")
    private String title;
    @NotBlank(message = "Content text cannot be blank.")
    private String content;
    @Positive(message = "User ID cannot be negative or zero.")
    private long user;
}
