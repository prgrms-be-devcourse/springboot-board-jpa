package com.prgrms.jpa.controller.dto.post.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class FindAllPostRequest {

    private Long postId;

    @Min(value = 1)
    private final int size;
}
